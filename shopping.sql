-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 27 avr. 2025 à 20:31
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `shopping`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateur`
--

DROP TABLE IF EXISTS `administrateur`;
CREATE TABLE IF NOT EXISTS `administrateur` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `administrateur`
--

INSERT INTO `administrateur` (`id_admin`, `nom`, `prenom`, `email`, `mot_de_passe`) VALUES
(1, 'a', 'a', 'a', 'a');

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id_article` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `description` text,
  `prix_unitaire` decimal(10,2) NOT NULL,
  `prix_vrac` decimal(10,2) DEFAULT NULL,
  `quantite_vrac` int DEFAULT NULL,
  `id_marque` int DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_article`),
  KEY `id_marque` (`id_marque`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `article`
--

INSERT INTO `article` (`id_article`, `nom`, `description`, `prix_unitaire`, `prix_vrac`, `quantite_vrac`, `id_marque`, `photo`) VALUES
(1, 'Robe', 'Robe élégante pour l\'été', 39.99, 35.00, 3, 2, 'images/robe.jpg'),
(2, 'Collier', 'Collier doré tendance', 19.99, 17.00, 5, 1, 'images/collier.jpeg'),
(3, 'Short', 'Short en jean décontracté', 24.99, 22.00, 3, 1, 'images/short.jpeg'),
(4, 'T-shirt', 'T-shirt coton bio', 14.99, 12.00, 4, 1, 'images/T-shirt.jpeg'),
(5, 'Chaussettes', 'Lot de chaussettes confortables', 5.99, 4.50, 6, 1, 'images/chaussettes.jpeg');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `type_client` enum('nouveau','ancien') NOT NULL,
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id_client`, `nom`, `prenom`, `email`, `mot_de_passe`, `type_client`) VALUES
(1, 'Coudert', 'Alice', 'alice.coudert@edu.ece.fr', 'alice', 'nouveau'),
(2, 'heitz', 'quentin', 'quentin.heitz@edu.ece.fr', 'quentin', 'nouveau'),
(3, 'a', 'a', 'a', 'a', 'ancien'),
(4, 'aaaa', 'a', 'aaa', 'aa', 'nouveau'),
(5, 'bb', 'zz', 'zz', 'z', 'nouveau');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id_commande` int NOT NULL AUTO_INCREMENT,
  `id_client` int DEFAULT NULL,
  `date_commande` datetime DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id_commande`),
  KEY `id_client` (`id_client`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `id_client`, `date_commande`, `total`) VALUES
(1, 3, '2025-04-20 13:01:18', 119.97),
(2, 3, '2025-04-20 20:50:11', 114.96),
(3, 3, '2025-04-27 21:49:00', 85.00),
(4, 3, '2025-04-27 22:09:08', 124.99),
(5, 3, '2025-04-27 22:24:25', 156.98),
(6, 3, '2025-04-27 22:25:26', 59.97);

-- --------------------------------------------------------

--
-- Structure de la table `commande_article`
--

DROP TABLE IF EXISTS `commande_article`;
CREATE TABLE IF NOT EXISTS `commande_article` (
  `id_commande` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int NOT NULL,
  `prix_total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_commande`,`id_article`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commande_article`
--

INSERT INTO `commande_article` (`id_commande`, `id_article`, `quantite`, `prix_total`) VALUES
(1, 1, 3, 105.00),
(2, 1, 1, 39.99),
(2, 3, 3, 66.00),
(3, 2, 5, 85.00),
(4, 1, 3, 105.00),
(4, 2, 1, 19.99),
(5, 5, 6, 27.00),
(5, 2, 6, 102.00),
(5, 3, 1, 24.99),
(6, 2, 3, 59.97);

-- --------------------------------------------------------

--
-- Structure de la table `historique_commande`
--

DROP TABLE IF EXISTS `historique_commande`;
CREATE TABLE IF NOT EXISTS `historique_commande` (
  `id_historique` int NOT NULL AUTO_INCREMENT,
  `id_client` int DEFAULT NULL,
  `id_commande` int DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`id_historique`),
  KEY `id_client` (`id_client`),
  KEY `id_commande` (`id_commande`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `marque`
--

DROP TABLE IF EXISTS `marque`;
CREATE TABLE IF NOT EXISTS `marque` (
  `id_marque` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  PRIMARY KEY (`id_marque`),
  UNIQUE KEY `nom` (`nom`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `marque`
--

INSERT INTO `marque` (`id_marque`, `nom`) VALUES
(1, 'ModeExpress'),
(2, 'Moda');

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `id_panier` int NOT NULL AUTO_INCREMENT,
  `id_client` int NOT NULL,
  `date_creation` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_panier`),
  KEY `id_client` (`id_client`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id_panier`, `id_client`, `date_creation`) VALUES
(1, 3, '2025-04-19 14:01:14'),
(2, -1, '2025-04-27 11:40:55');

-- --------------------------------------------------------

--
-- Structure de la table `panier_article`
--

DROP TABLE IF EXISTS `panier_article`;
CREATE TABLE IF NOT EXISTS `panier_article` (
  `id_panier` int NOT NULL,
  `id_article` int NOT NULL,
  `quantite` int NOT NULL,
  PRIMARY KEY (`id_panier`,`id_article`),
  KEY `id_article` (`id_article`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `panier_article`
--

INSERT INTO `panier_article` (`id_panier`, `id_article`, `quantite`) VALUES
(2, 2, 5),
(2, 1, 4);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
