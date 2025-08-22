package codes.blackjack.minelittlepony.mixin;

import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface MixinExtLivingEntity {
	@Accessor("jumping")
	boolean isJumping();

	@Accessor("stepBobbingAmount")
	float getStepBobbingAmount();

	@Accessor("prevStepBobbingAmount")
	float getPrevStepBobbingAmount();

	@Accessor("texture")
	void setTexture(String texture);
}
