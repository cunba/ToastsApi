-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 22-12-2021 a las 19:25:30
-- Versión del servidor: 10.4.21-MariaDB
-- Versión de PHP: 8.0.13

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
  `location` varchar(50) NOT NULL,
  `open` tinyint(1) NOT NULL,
  `punctuation` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `establishments`
--

INSERT INTO `establishments` (`id`, `name`, `creation_date`, `location`, `open`, `punctuation`) VALUES
(1, 'Criollo', '2021-12-12', '41.64885072522205, -0.8858524785997504', 1, 5),
(2, 'Mondo', '2021-12-12', '41.6536650638226, -0.8843359286918279', 1, 5),
(3, 'Matisse', '2021-12-08', '41.658609763434804, -0.8756923112958851', 1, 0);

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

--
-- Volcado de datos para la tabla `menus`
--

INSERT INTO `menus` (`id`, `date`, `price`, `punctuation`) VALUES
(1, '2021-12-12', '3.50', '3.5'),
(2, '2021-12-12', '3.00', '5.0'),
(3, '2021-12-12', '3.00', '5.0'),
(4, '2021-12-12', '3.00', '5.0'),
(5, '2021-12-17', '3.00', '5.0');

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

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`id`, `date`, `in_menu`, `price`, `punctuation`, `type_id`, `menu_id`, `publication_id`) VALUES
(1, '2021-12-12', 1, 1, 0, 2, 2, 1),
(2, '2021-12-12', 1, 0, 0, 2, 1, 1),
(3, '2021-12-12', 1, 0, 0, 2, 1, 3),
(4, '2021-12-12', 0, 0, 0, 14, NULL, 3),
(5, '2021-12-12', 0, 0, 0, 10, NULL, 3),
(6, '2021-12-12', 0, 0, 0, 10, NULL, 1),
(7, '2021-12-19', 0, 2, 5, 2, NULL, 1);

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

--
-- Volcado de datos para la tabla `publications`
--

INSERT INTO `publications` (`id`, `date`, `total_price`, `total_punctuation`, `photo`, `establishment_id`, `user_id`) VALUES
(1, '2021-12-12', 9.5, 2.25, 'photo.jpg', 1, 3),
(2, '2021-12-12', 0, 0, 'string.jpg', 2, 2),
(3, '2021-12-12', 0, 0, 'string.jpg', 2, 1),
(5, '2021-12-19', 0, 0, 'string.jpg', 2, 3),
(6, '2021-12-19', 0, 0, 'string.jpg', 2, 1),
(7, '2021-12-19', 0, 0, 'string.jpg', 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `money_spent` float DEFAULT NULL,
  `publications_number` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `birth_date`, `email`, `password`, `creation_date`, `active`, `money_spent`, `publications_number`) VALUES
(1, 'Irene', 'Cunto', '1995-09-05', 'ire.cunba@gmail.com', '282629_Pruebita', '2021-12-19', 1, 0, 1),
(2, 'Marta', 'Bagüés', '1997-06-18', 'martabags@gmail.com', 'suidgkl', '2021-12-19', 1, 0, 1),
(3, 'Carmen', 'Baranda', '1961-07-20', 'martabags@gmail.com', '282629_Mama', '2021-12-12', 1, 9, 2);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `menus`
--
ALTER TABLE `menus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `products_types`
--
ALTER TABLE `products_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `publications`
--
ALTER TABLE `publications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`),
  ADD CONSTRAINT `fk_publication_id` FOREIGN KEY (`publication_id`) REFERENCES `publications` (`id`),
  ADD CONSTRAINT `fk_type_id` FOREIGN KEY (`type_id`) REFERENCES `products_types` (`id`);

--
-- Filtros para la tabla `publications`
--
ALTER TABLE `publications`
  ADD CONSTRAINT `fk_establishment_id` FOREIGN KEY (`establishment_id`) REFERENCES `establishments` (`id`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
