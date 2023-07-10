-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 23 Cze 2023, 16:19
-- Wersja serwera: 10.4.20-MariaDB
-- Wersja PHP: 7.3.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `project_todo_list`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `action_counter`
--

CREATE TABLE `action_counter` (
  `id` bigint(20) NOT NULL,
  `action_name` varchar(255) NOT NULL,
  `counter` int(11) NOT NULL DEFAULT 0,
  `update_on` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `action_counter`
--

INSERT INTO `action_counter` (`id`, `action_name`, `counter`, `update_on`) VALUES
(1, 'read_all', 0, NULL),
(2, 'read_with_filter', 0, NULL),
(3, 'read_one', 0, NULL),
(4, 'create', 0, NULL),
(5, 'update', 0, NULL),
(6, 'delete', 0, NULL);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `action_counter`
--
ALTER TABLE `action_counter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_2voksw56dgj1igw1rcymai08b` (`action_name`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `action_counter`
--
ALTER TABLE `action_counter`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
