# Mise en œuvre de l'Injection des Dépendances

## Introduction
Ce projet est une mise en pratique de l'injection des dépendances en Java. L'objectif est d'implémenter une architecture avec un couplage faible en utilisant différentes techniques d'injection des dépendances :
1. Instanciation statique
2. Instanciation dynamique
3. Utilisation du framework Spring (XML et annotations)

## Structure du Projet
Le projet est composé des interfaces et classes suivantes :

### 1. Interface `IDao`
Cette interface définit une méthode `getData()` qui sera implémentée par plusieurs classes.

```java
public interface IDao {
    double getData();
}
```

### 2. Implémentation de `IDao`
Deux classes net.kacemi.dao.`DaoImpl` et net.kacemi.ext.`DaoImpl2`qui implémente `IDao` en fournissant 2 versions (capteurs et base de données).

net.kacemi.dao.`DaoImpl`

```java
import org.springframework.stereotype.Component;

@Component("d")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version base de données");
        double temp = 25;
        return temp;
    }
}
```
##
net.kacemi.ext.`DaoImpl2`
```java
@Component("d2")
public class DaoImpl2 implements IDao {
    @Override
    public double getData() {
        System.out.println("Version capteurs...");
        return 12.5;
    }
}
```

### 3. Interface `IMetier`
Cette interface définit une méthode `calcul()`.

```java
public interface IMetier {
    double calcul();
}
```

### 4. Implémentation de `IMetier`
Une classe `MetierImpl` qui dépend de `IDao`.

```java
@Component("metier")
public class MetierImpl implements IMetier {
    
    
    IDao dao;

// On doit l'enlever pour faire instantation par constructeur et eviter "this.dao" is null
//    public MetierImpl() {
//
//    }

    /*
    Pour injecter un objet d'une classe qui implémente l'interface IDao pendant l'instantiation
     */
    public MetierImpl(@Qualifier("d2") IDao dao) {
        this.dao = dao;
    }

    /*
    Pour injecter un objet d'une classe qui implémente l'interface IDao
     */
    public void setDao(IDao dao){
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        return (t*Math.PI/180.0 + Math.atan(t))*100/((Math.random()+0.1)*50);
    }
}
```

## Injection des Dépendances

### 1. Instanciation Statique
Dans cette approche, les dépendances sont créées directement dans le code.

```java
public class PresStatique {
    public static void main(String[] args) {
        IDao dao = new DaoImpl();
        IMetier metier = new MetierImpl(dao);
        System.out.println("res=" + metier.calcul());
    }
}
```

### 2. Instanciation Dynamique (via fichier de configuration)
Nous utilisons la réflexion pour instancier dynamiquement les classes sans les lier directement.

>[!NOTE]
>On doit créer un fichier config.txt dans la racine de projet
```java
package net.kacemi.pres;

import net.kacemi.dao.IDao;
import net.kacemi.metier.IMetier;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class PresDynamique {
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Scanner sc = new Scanner(new File("config.txt"));
        //lire la classe dao à partir du fichier config
        String daoClassName = sc.nextLine();
        Class cDao = Class.forName(daoClassName);
        Object dao = (IDao) cDao.newInstance();

        String metierClassName = sc.nextLine();
        Class cMetier = Class.forName(metierClassName);
        IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

        System.out.println("using config.txt");
        System.out.println("res="+metier.calcul());
    }
}

```

### 3. Injection avec Spring
#### a. Version XML
Fichier `config.xml` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="d" class="net.kacemi.dao.DaoImpl" />
    <bean id="metier" class="net.kacemi.metier.MetierImpl">
        <constructor-arg ref="d" />
        <!--<property name="dao" ref="d" /> -->
    </bean>
</beans>
```

Dans `PresSpringXML.java` :

```java
package net.kacemi.pres;

import net.kacemi.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresSpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
        IMetier metier = (IMetier) context.getBean("metier");

        System.out.println("RES= "+ metier.calcul());
    }
}

```

#### b. Version Annotations

Ajout des annotations dans les classes :

`@Component` : Utilisée pour déclarer un bean Spring.

`@Autowired` : Permet l'injection automatique des dépendances.

`@Qualifier` : Évite les conflits lorsqu'il y a plusieurs implémentations de la même interface.

Dans `PresSpringAnnotations.java` :

```java
package net.kacemi.pres;

import net.kacemi.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotations {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("net.kacemi");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("RES= "+metier.calcul());
    }
}
```

## Conclusion
Dans ce projet, nous avons exploré plusieurs techniques pour injecter des dépendances :
- **Instanciation statique** : simple mais couplage fort.
- **Instanciation dynamique** : plus flexible grâce à la réflexion.
- **Spring (XML & Annotations)** : approche standard pour les applications professionnelles.

L'utilisation de Spring simplifie grandement la gestion des dépendances et améliore la maintenabilité du code.

---

📌 **Lien du repository GitHub** : https://github.com/ayman-kacemi47/inversionDeControle

🚀 **Dernière mise à jour** : [16/03/2025 13:00]

