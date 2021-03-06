# MulliganSim
Mulligan Simulator for Shadowverse


Formatting for files should be as follows:


DECKLIST FORMATTING

Card Name,Quantity

WARNING: Abbreviate cards with commas in their name (such as Albert, Levin Saber) or things will break.

Example: 3 Albert, Levin Sabers in deck

Albert,3


MULLIGAN RULES FORMATTING

Conditions:Cards to keep


Separate Conditions and Cards to keep with commas. The comma represents an AND on the conditions side and represents an OR on the cards side.

Example: I want to keep Novice Trooper and Princess Juliet if I have Vanguard and Princess Vanguard

Vanguard,Princess Vanguard:Novice Trooper,Princess Juliet


KEYWORDS

KEEP: Putting KEEP as your only condition means the following cards will be kept no matter what

Example: I want to keep Insight and Magic Missile no matter what

KEEP:Insight,Magic Missile


FIRST: Put FIRST before any conditions if and only if you only want to apply them when going first

Example: I want to keep Feena if I'm going first and I have Fairy Circle

FIRST,Fairy Circle:Feena


Same applies for SECOND, except it applies on going second instead

Example: I want to keep Elven Princess Mage if I'm going second

SECOND:Elven Princess Mage


Simulations are done with a 50/50 on first/second and 100000 mulligans.


GENERAL GUIDELINES FOR MULLIGAN SIM USAGE


Make sure card naming is consistent in your decklist and your conditions


Do not add impossible conditions such as Vanguard,Quickblader,Ninja Trainee,Princess Vanguard,Mentor's Teachings:Albert that will never be fulfilled

HOWEVER, since the calculations include first draw, conditions such as Vanguard,Princess Vanguard,Novice Trooper:Ephemera that will never be true in the mulligan should also be used if they can be true after drawing turn 1.
