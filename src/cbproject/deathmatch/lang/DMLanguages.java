package cbproject.deathmatch.lang;

import cbproject.core.register.CBCLanguage;
import cbproject.deathmatch.register.DMItems;

public class DMLanguages {
	
	public static void register(){
		
		CBCLanguage.addDefaultName(DMItems.weapon_crowbar, "Crowbar");
		CBCLanguage.addDefaultName(DMItems.weapon_shotgun, "Shotgun");
		CBCLanguage.addDefaultName(DMItems.weapon_9mmhandgun , "9mm Handgun");
		CBCLanguage.addDefaultName(DMItems.weapon_9mmAR, "9mmAR");
        CBCLanguage.addDefaultName(DMItems.weapon_hgrenade, "Hand Grenade");
        CBCLanguage.addDefaultName(DMItems.weapon_357, ".357 Magnum");
        CBCLanguage.addDefaultName(DMItems.weapon_satchel, "Satchel");
        CBCLanguage.addDefaultName(DMItems.weapon_RPG, "RPG Rocket Launcher");
        CBCLanguage.addDefaultName(DMItems.weapon_crossbow, "High Heat Crossbow");
        CBCLanguage.addDefaultName(DMItems.weapon_gauss, "Gauss Gun");
        CBCLanguage.addDefaultName(DMItems.weapon_egon, "Egon");
        CBCLanguage.addDefaultName(DMItems.armorHEVBoot, "HEV boot");
		CBCLanguage.addDefaultName(DMItems.armorHEVHelmet, "HEV helmet");
		CBCLanguage.addDefaultName(DMItems.armorHEVChestplate, "HEV chestplate");
		CBCLanguage.addDefaultName(DMItems.armorHEVLeggings, "HEV leggings");
       
		CBCLanguage.addLocalName(DMItems.weapon_crowbar, "物理学圣剑");
		CBCLanguage.addLocalName(DMItems.weapon_shotgun, "霰弹枪");
		CBCLanguage.addLocalName(DMItems.weapon_9mmhandgun , "9毫米手枪");
		CBCLanguage.addLocalName(DMItems.weapon_9mmAR, "9毫米步枪");
        CBCLanguage.addLocalName(DMItems.weapon_hgrenade, "手雷");
        CBCLanguage.addLocalName(DMItems.weapon_357, ".357麦林枪");
        CBCLanguage.addLocalName(DMItems.weapon_satchel, "遥控炸药");
        CBCLanguage.addLocalName(DMItems.weapon_RPG, "RPG火箭发射器");
        CBCLanguage.addLocalName(DMItems.weapon_crossbow, "复合十字弩");
        CBCLanguage.addLocalName(DMItems.weapon_gauss, "高斯枪");
        CBCLanguage.addLocalName(DMItems.weapon_egon, "离子光束枪");
        CBCLanguage.addLocalName(DMItems.armorHEVBoot, "HEV靴");
		CBCLanguage.addLocalName(DMItems.armorHEVHelmet, "HEV盔");
		CBCLanguage.addLocalName(DMItems.armorHEVChestplate, "HEV甲");
		CBCLanguage.addLocalName(DMItems.armorHEVLeggings, "HEV腿");
		
		CBCLanguage.addDefaultName("mode.new", "New Mode");
		CBCLanguage.addDefaultName("mode.357", "Normal Mode");
		CBCLanguage.addDefaultName("mode.9mmar1","Automatic Mode");
		CBCLanguage.addDefaultName("mode.9mmar2","Grenade Mode");
		CBCLanguage.addDefaultName("mode.9mmhg1","Semi Automatic Mode");
		CBCLanguage.addDefaultName("mode.9mmhg2","Full Automatic Mode");
		CBCLanguage.addDefaultName("mode.bow1","Normal Shot");
		CBCLanguage.addDefaultName("mode.bow2","Explosive Shot");
		CBCLanguage.addDefaultName("mode.egon","Normal Mode");
		CBCLanguage.addDefaultName("mode.gauss1","Automatic Mode");
		CBCLanguage.addDefaultName("mode.gauss2","Charge Mode");
		CBCLanguage.addDefaultName("mode.rpg","Normal Mode");
		CBCLanguage.addDefaultName("mode.satchel1","Setting Mode");
		CBCLanguage.addDefaultName("mode.satchel2","Detonating Mode");
		CBCLanguage.addDefaultName("mode.sg1","Single Shot Mode");
		CBCLanguage.addDefaultName("mode.sg2","Double Shot Mode");
		
		CBCLanguage.addLocalName("mode.new", "新模式");
		CBCLanguage.addLocalName("mode.357", "普通射击模式");
		CBCLanguage.addLocalName("mode.9mmar1","自动射击模式");
		CBCLanguage.addLocalName("mode.9mmar2","榴弹发射模式");
		CBCLanguage.addLocalName("mode.9mmhg1","半自动射击模式");
		CBCLanguage.addLocalName("mode.9mmhg2","全自动射击模式");
		CBCLanguage.addLocalName("mode.bow1","普通射击模式");
		CBCLanguage.addLocalName("mode.bow2","爆炸射击模式");
		CBCLanguage.addLocalName("mode.egon","普通模式");
		CBCLanguage.addLocalName("mode.gauss1","自动射击模式");
		CBCLanguage.addLocalName("mode.gauss2","蓄电射击模式");
		CBCLanguage.addLocalName("mode.rpg","普通射击模式");
		CBCLanguage.addLocalName("mode.satchel1","放置模式");
		CBCLanguage.addLocalName("mode.satchel2","引爆模式");
		CBCLanguage.addLocalName("mode.sg1","单发射击模式");
		CBCLanguage.addLocalName("mode.sg2","双发射击模式");
		
		CBCLanguage.addDefaultName("crafter.weapon", "Weapon Forging");
		CBCLanguage.addDefaultName("crafter.ammo", "Ammo Forging");
		CBCLanguage.addDefaultName("crafter.storage", "Material Storage");
		
		CBCLanguage.addLocalName("crafter.weapon", "武器制造");
		CBCLanguage.addLocalName("crafter.ammo", "弹药制造");
		CBCLanguage.addLocalName("crafter.storage", "材料存放");
		
	}
	
}
