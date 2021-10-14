Events.on(ClientLoadEvent,
    e => {
        let unit = Vars.content.units().find(u => u.name == "endless-rusting-guardian-sulphur-stingray");
        Log.info("hai");
        if(Version.number > 6 && !Vars.headless){
            unit.omniMovement = true;
            //unit.targetFlags = [BlockFlag.turret, null];
        }
        else unit.targetFlag = BlockFlag.turret;
    }
);

let pulseInfected = extend(Team, 115, 
                           name: "Pulse Infected",
                           color: Color.valueOf("#5c79d0"), 
                           {pallet: [Color.valueOf("#646eb2"), Color.valueOf("#6977d6"), ("#8199e1")]}
                          );
Vars.mods.getScripts().runConsole("Object.asign(Team.all[116], {name: \"Void Infected\", color: Color.valueOf(\"#33355b\"), palete: [Color.valueOf(\"#6a5c88\"), Color.valueOf(\"#ac8ac1\"), Color.valueOf(\"#33355b\")]});")