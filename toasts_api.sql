-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-04-2022 a las 00:19:08
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 7.4.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `toasts_api`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `establishments`
--

CREATE TABLE `establishments` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `creation_date` date NOT NULL,
  `location` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`location`)),
  `open` tinyint(1) NOT NULL,
  `punctuation` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `establishments`
--

INSERT INTO `establishments` (`id`, `name`, `creation_date`, `location`, `open`, `punctuation`) VALUES
(1, 'Criollo', '2022-04-17', '{\"latitude\": 0.0, \"longitude\": 0.0}', 1, 0),
(6, 'Doña hipólita', '2022-04-20', '{\"latitude\":2.0,\"longitude\":3.0}', 1, 0),
(16, 'Mondo', '2022-04-20', '{\"latitude\":41.653664,\"longitude\":-0.88433594}', 1, 0),
(17, 'Mezcalito', '2022-04-20', '{\"latitude\":41.653664,\"longitude\":-0.88433594}', 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menus`
--

CREATE TABLE `menus` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `price` decimal(4,2) NOT NULL,
  `punctuation` decimal(2,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `in_menu` tinyint(1) NOT NULL,
  `price` float NOT NULL,
  `punctuation` float NOT NULL,
  `type_id` int(11) NOT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `publication_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products_types`
--

CREATE TABLE `products_types` (
  `id` int(11) NOT NULL,
  `product_name` varchar(20) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `products_types`
--

INSERT INTO `products_types` (`id`, `product_name`, `type`) VALUES
(1, 'Café', 'solo'),
(2, 'Café', 'con leche'),
(3, 'Té', 'verde'),
(4, 'Té', 'rojo'),
(5, 'Té', 'de frutas del bosque'),
(6, 'Té', 'negro'),
(7, 'Té', 'matcha con leche de avena'),
(8, 'Tostada', 'de tomate'),
(9, 'Tostada', 'de aguacate'),
(10, 'Tostada', 'de jamón'),
(11, 'Tostada', 'de aguacate, tomate y huevo'),
(12, 'Tostada', 'de aguacate y mozzarella'),
(13, 'Tostada', 'de aguacate, tomate y mozarella'),
(14, 'Tostada', 'de jamón y queso'),
(15, 'Tostada', 'de york y queso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publications`
--

CREATE TABLE `publications` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `total_price` float NOT NULL,
  `total_punctuation` float NOT NULL,
  `photo` varchar(50) NOT NULL,
  `establishment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `money_spent` float DEFAULT NULL,
  `publications_number` int(10) DEFAULT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `username`, `name`, `surname`, `birth_date`, `email`, `password`, `creation_date`, `active`, `money_spent`, `publications_number`, `role`) VALUES
(4, 'cunba', 'Irene', 'Cunto', '1995-09-05', 'ire.cunba@gmail.com', '$2a$10$qkF8AeNbwcxcGY8fFrZaoOtSL5J9FssXUh1itShLUfjmCI4lzEufG', '2022-04-16', 1, 0, 0, 'USER'),
(5, 'martabags', 'Marta', 'Bagüés', '1997-06-18', 'martabags@gmail.com', '$2a$10$pThnLtTGDTc5y72AD7Nm9.ppqDZNXBajpekjUBpYBjl5bTCGMiHCu', '2022-04-17', 1, 0, 0, 'USER'),
(6, 'admin', 'admin', 'admin', '1997-06-18', 'admin', '$2a$10$2ZIh3vyn/VtbenhBAqqTy.oZ1pTEPVL9Kom7ziwOqmXGmIUiqUh9W', '2022-04-20', 1, 0, 0, 'ADMIN');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `establishments`
--
ALTER TABLE `establishments`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_coffee_menu_id` (`menu_id`),
  ADD KEY `fk_coffee_publication_id` (`publication_id`),
  ADD KEY `fk_type_id` (`type_id`);

--
-- Indices de la tabla `products_types`
--
ALTER TABLE `products_types`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `publications`
--
ALTER TABLE `publications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_establishment_id` (`establishment_id`),
  ADD KEY `fk_user_id` (`user_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `establishments`
--
ALTER TABLE `establishments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `products_types`
--
ALTER TABLE `products_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `publications`
--
ALTER TABLE `publications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKd0f0vquvtnwajble9jhov32k0` FOREIGN KEY (`type_id`) REFERENCES `products_types` (`id`),
  ADD CONSTRAINT `FKeajv8kuchlc88i201oxwnh0ig` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`),
  ADD CONSTRAINT `FKk42pinsd2bghq49ar1x4q0qjt` FOREIGN KEY (`publication_id`) REFERENCES `publications` (`id`),
  ADD CONSTRAINT `fk_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`),
  ADD CONSTRAINT `fk_publication_id` FOREIGN KEY (`publication_id`) REFERENCES `publications` (`id`),
  ADD CONSTRAINT `fk_type_id` FOREIGN KEY (`type_id`) REFERENCES `products_types` (`id`);

--
-- Filtros para la tabla `publications`
--
ALTER TABLE `publications`
  ADD CONSTRAINT `FK13mav6j1vppd6qxerj5ls1bl8` FOREIGN KEY (`establishment_id`) REFERENCES `establishments` (`id`),
  ADD CONSTRAINT `FKmjejkepfqnuud5mcuffv5a21u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_establishment_id` FOREIGN KEY (`establishment_id`) REFERENCES `establishments` (`id`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
