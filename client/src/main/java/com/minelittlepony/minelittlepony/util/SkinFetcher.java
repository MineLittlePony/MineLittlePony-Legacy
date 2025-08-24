package com.minelittlepony.minelittlepony.util;

import com.minelittlepony.minelittlepony.MineLPEntry;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * Utility class to get player information and skins from Mojang's skin servers.
 * Yeah, we could do some fancy stuff with proper JSON models, but honestly, I just
 * don't really feel like it.
 */
public final class SkinFetcher {
	private static final Gson GSON = new Gson();
	private static final CloseableHttpClient httpClient = HttpClients.createDefault();

	private SkinFetcher() {
	}

	public static Optional<String> getSkinUrl(String playerName) throws SkinFetchException {
		try {
			Optional<String> valhallaSkinUrl = getValhallaSkinUrl(playerName);

			if (valhallaSkinUrl.isPresent()) {
				return valhallaSkinUrl;
			}
		} catch (SkinFetchException ignored) {
		}

		return getMojangSkinUrl(playerName);
	}

	private static Optional<String> getValhallaSkinUrl(String playerName) throws SkinFetchException {
		Optional<String> playerUuid = getPlayerUuid(playerName);
		String skinUrl;

		try (CloseableHttpResponse response = httpClient.execute(
			new HttpGet("https://skins.minelittlepony-mod.com/api/v1/user/" + playerUuid.get())
		)) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				if (statusCode == 404) {
					MineLPEntry.LOGGER.debug("Valhalla API returned 404 for player skin for {}", playerName);
					return Optional.empty();
				}

				throw new SkinFetchException("Valhalla API returned non-200 code " + response.getStatusLine().getStatusCode() + " for player skin fetch.");
			}

			HttpEntity entity = response.getEntity();

			JsonObject jsonResponse = GSON.fromJson(
				new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8), JsonObject.class
			);

			if (!jsonResponse.has("textures")
				|| !jsonResponse.get("textures").isJsonObject()) {
				MineLPEntry.LOGGER.debug("Valhalla API returned no textures for player name {}", playerName);
				return Optional.empty();
			}

			JsonObject texturesResponse = jsonResponse.getAsJsonObject("textures");

			if (!texturesResponse.has("skin")
				|| !texturesResponse.get("skin").isJsonObject()) {
				MineLPEntry.LOGGER.debug("Valhalla API returned no skin texture for player name {}", playerName);
				return Optional.empty();
			}

			JsonObject skinTexture = texturesResponse.getAsJsonObject("skin");

			if (!skinTexture.has("url")
				|| !skinTexture.get("url").isJsonPrimitive()) {
				MineLPEntry.LOGGER.debug("Valhalla API returned no URL for skin texture for player name {}", playerName);
				return Optional.empty();
			}

			skinUrl = skinTexture.get("url").getAsString();

			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new SkinFetchException("I/O error when fetching skin for player " + playerName, e);
		}

		return Optional.of(skinUrl);
	}

	private static Optional<String> getMojangSkinUrl(String playerName) throws SkinFetchException {
		Optional<String> playerUuid = getPlayerUuid(playerName);

		if (!playerUuid.isPresent()) {
			return Optional.empty();
		}

		String skinUrl;

		try (CloseableHttpResponse response = httpClient.execute(
			new HttpGet("https://sessionserver.mojang.com/session/minecraft/profile/" + playerUuid.get())
		)) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				if (statusCode == 404) {
					MineLPEntry.LOGGER.debug("No skin found for player {}", playerName);
					return Optional.empty();
				}

				throw new SkinFetchException("Mojang API returned non-200 code " + statusCode + " for player skin fetch.");
			}
			HttpEntity entity = response.getEntity();

			JsonObject jsonResponse = GSON.fromJson(
				new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8),
				JsonObject.class
			);

			if (!jsonResponse.has("properties")) {
				MineLPEntry.LOGGER.debug("Mojang API returned no properties for player skin fetch for {}", playerName);
				return Optional.empty();
			}

			JsonArray jsonProperties =  jsonResponse.getAsJsonArray("properties");
			Optional<String> encodedTextures = extractTexturesPayload(jsonProperties);

			if (!encodedTextures.isPresent()) {
				return Optional.empty();
			}

			String decodedTextures = new String(
				Base64.getDecoder().decode(encodedTextures.get()),
				StandardCharsets.UTF_8
			);

			JsonObject jsonTextures = GSON.fromJson(decodedTextures, JsonObject.class);
			if (!jsonTextures.has("textures") || !jsonTextures.get("textures").isJsonObject()) {
				MineLPEntry.LOGGER.debug("Mojang API returned no textures value for player {}", playerName);
				return Optional.empty();
			}

			JsonObject jsonTextureTypes = jsonTextures.getAsJsonObject("textures");

			if (!jsonTextureTypes.has("SKIN")
				|| !jsonTextureTypes.get("SKIN").isJsonObject()) {
				MineLPEntry.LOGGER.debug("Mojang API returned no skin texture for player {}", playerName);
				return Optional.empty();
			}

			JsonObject jsonTexture = jsonTextureTypes.getAsJsonObject("SKIN");
			if (!jsonTexture.has("url")
				|| !jsonTexture.get("url").isJsonPrimitive()) {
				MineLPEntry.LOGGER.debug("Mojang API returned no skin URL for player {}.", playerName);
			}

			skinUrl = jsonTexture.get("url").getAsString();

			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new SkinFetchException("I/O error when fetching Mojang skin for player " + playerName, e);
		}

		return Optional.ofNullable(skinUrl);
	}

	private static @NotNull Optional<String> extractTexturesPayload(Iterable<JsonElement> properties) {
		String encodedTextures = null;

		for (JsonElement jsonElement : properties) {
			if (jsonElement.isJsonObject()) {
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				if (jsonObject.has("name")
					&& jsonObject.get("name").isJsonPrimitive()
					&& jsonObject.get("name").getAsString().equals("textures")
					&& jsonObject.has("value")
					&& jsonObject.get("value").isJsonPrimitive()) {
					encodedTextures = jsonObject.get("value").getAsString();
				}
			}
		}

		if (encodedTextures == null || encodedTextures.isEmpty()) {
			MineLPEntry.LOGGER.debug("Mojang API returned no textures property for player skin fetch.");
			return Optional.empty();
		}

		return Optional.of(encodedTextures);
	}

	private static Optional<String> getPlayerUuid(String playerName) throws SkinFetchException {
		String playerUuid;

		try (CloseableHttpResponse response = httpClient.execute(
			new HttpGet("https://api.mojang.com/users/profiles/minecraft/" + playerName)
		)) {
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				if (statusCode == 404) {
					MineLPEntry.LOGGER.debug("No profile found for player {}", playerName);
					return Optional.empty();
				}
				throw new SkinFetchException("Mojang API returned non-200 code " +  response.getStatusLine().getStatusCode() + " for player profile fetch.");
			}

			HttpEntity entity = response.getEntity();
			JsonObject jsonResponse = GSON.fromJson(
				new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8),
				JsonObject.class
			);

			if (!jsonResponse.has("id")
				|| !jsonResponse.get("id").isJsonPrimitive()) {
				MineLPEntry.LOGGER.debug("Mojang API returned no ID for player profile fetch.");
				return Optional.empty();
			}

			playerUuid = jsonResponse.get("id").getAsString();

			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return Optional.ofNullable(playerUuid);
	}

	public static class SkinFetchException extends Exception {
		public SkinFetchException(String message) {
			super(message);
		}

		public SkinFetchException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
