# Quoi de neuf pour la version 1.1
## Resolution de probleme des ressources images.
On a réglé le probleme des ressources pour le .jar
Cela est fait, on utilisant les premitives ``` getClass().getRessources ``` pour recuperer les image   au lieu des liens absolues.

## Resolution de probleme de sauvegarde sous MAC
Ce probleme était FileNotFoundException, car la maniere dont on a créé le fichier n'était générique pour tous les platformes.
On a reglé le probleme on créant le fichier de maniere Générique pour tous les OS.

## Resolution de probleme d'affichage sous MAC
On a resolu et testé notre implémantation sous MAC OS Sierra 10.12;
Le probleme c'était due a la valeurs par default de "Opaque" des composants swing, où cet attribut vaut true dans certain LookAndFeel
et false dans autres. Ce probleme était résolu en ajoutant les instructions suivant sur les composants swing qui n'apparaissent pas.

```java
composant.setBorderPainted(false)
composant.setOpaque(true)
```

Pour plus d'information visiter le [lien](https://docs.oracle.com/javase/tutorial/uiswing/painting/problems.html)

### ScreenShots
![img01](mac_01.jpg)
![img02](mac_02.jpg)
![img03](mac_03.jpg)
![img04](mac_04.jpg)
