package codes.blackjack.minelittlepony.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
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

public final class SkinFetcher {
	private static final Gson GSON = new Gson();

	private SkinFetcher() {
	}

	public static String getSkinUrl(String playerName) {
		CloseableHttpClient httpClient = HttpClients.createDefault();

		String playerUuid;

		try (CloseableHttpResponse response = httpClient.execute(
			new HttpGet("https://api.mojang.com/users/profiles/minecraft/" + playerName)
		)) {
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Mojang API returned non-200 code " +  response.getStatusLine().getStatusCode() + " for player profile fetch.");
			}

			HttpEntity entity = response.getEntity();
			JsonObject jsonResponse = GSON.fromJson(
				new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8),
				JsonObject.class
			);

			if (!jsonResponse.has("id")
				|| !jsonResponse.get("id").isJsonPrimitive()) {
				throw new RuntimeException("Mojang API returned no ID for player profile fetch.");
			}

			playerUuid = jsonResponse.get("id").getAsString();

			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		String skinUrl;

		try (CloseableHttpResponse response = httpClient.execute(
			new HttpGet("https://sessionserver.mojang.com/session/minecraft/profile/" + playerUuid)
		)) {
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Mojang API returned non-200 code " + response.getStatusLine().getStatusCode() + " for player skin fetch.");
			}
			HttpEntity entity = response.getEntity();

			JsonObject jsonResponse = GSON.fromJson(
				new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8),
				JsonObject.class
			);

			if (!jsonResponse.has("properties")) {
				throw new RuntimeException("Mojang API returned no properties for player skin fetch.");
			}

			JsonArray jsonProperties =  jsonResponse.getAsJsonArray("properties");
			String encodedTextures = extractTexturesPayload(jsonProperties);

			String decodedTextures = new String(
				Base64.getDecoder().decode(encodedTextures),
				StandardCharsets.UTF_8
			);

			JsonObject jsonTextures = GSON.fromJson(decodedTextures, JsonObject.class);
			if (!jsonTextures.has("textures") || !jsonTextures.get("textures").isJsonObject()) {
				throw new RuntimeException("Mojang API returned no textures value for player skin fetch.");
			}

			JsonObject jsonTextureTypes = jsonTextures.getAsJsonObject("textures");

			if (!jsonTextureTypes.has("SKIN")
				|| !jsonTextureTypes.get("SKIN").isJsonObject()) {
				throw new RuntimeException("Mojang API returned no skin texture for player skin fetch.");
			}

			JsonObject jsonTexture = jsonTextureTypes.getAsJsonObject("SKIN");
			if (!jsonTexture.has("url")
				|| !jsonTexture.get("url").isJsonPrimitive()) {
				throw new RuntimeException("Mojang API returned no skin URL for player skin fetch.");
			}

			skinUrl = jsonTexture.get("url").getAsString();

			EntityUtils.consume(entity);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return skinUrl;
	}

	private static @NotNull String extractTexturesPayload(JsonArray properties) {
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
			throw new RuntimeException("Mojang API returned no textures property for player skin fetch.");
		}
		return encodedTextures;
	}

	private static class MojangProfileResponse {
		@SerializedName("id")
		private String id;
	}
}
