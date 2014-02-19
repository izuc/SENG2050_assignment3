-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 25, 2011 at 02:45 AM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE IF NOT EXISTS `comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `document_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment` varchar(100) NOT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE IF NOT EXISTS `documents` (
  `document_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `description` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `files`
--

CREATE TABLE IF NOT EXISTS `files` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `document_id` int(11) NOT NULL,
  `location` varchar(25) NOT NULL,
  `description` text NOT NULL,
  `date_uploaded` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `group_name` varchar(25) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`group_id`, `project_id`, `group_name`) VALUES
(1, 1, 'Team 1'),
(2, 1, 'Team 2'),
(3, 1, 'Team 3'),
(5, 1, 'Team 5'),
(6, 1, 'Team 4'),
(7, 1, 'Team 6');

-- --------------------------------------------------------

--
-- Table structure for table `group_members`
--

CREATE TABLE IF NOT EXISTS `group_members` (
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `group_members`
--

INSERT INTO `group_members` (`group_id`, `user_id`, `status`) VALUES
(1, 1, 1),
(1, 3, 1),
(2, 1, 1),
(3, 1, 1),
(3, 3, 1),
(5, 1, 1),
(6, 1, 1),
(7, 1, 1),
(7, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `meeting`
--

CREATE TABLE IF NOT EXISTS `meeting` (
  `meeting_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `location` varchar(100) NOT NULL,
  `datetime` datetime DEFAULT NULL,
  `agenda` varchar(100) NOT NULL,
  `minutes` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `meeting`
--

INSERT INTO `meeting` (`meeting_id`, `group_id`, `location`, `datetime`, `agenda`, `minutes`, `status`) VALUES
(1, 1, 'OIC', '2011-10-25 02:00:00', 'Blah', 'fgfdgdfgdfgdfg', 1),
(2, 1, 'OIC', NULL, 'Test', '', 2),
(3, 1, 'xxcvcxvcxvcxv', NULL, 'cvcxvcxvcxvxcv', '', 2),
(4, 1, 'fdfdgfgfdgdf', NULL, 'sdfdsfdsfsfsdfsdf', '', 2),
(5, 1, 'Test', '2011-10-27 00:00:00', 'Testing', 'hjghubh bhbhjhnb', 1),
(6, 1, 'Testing Blah', '2011-10-25 19:00:00', 'Blahing', 'Tetsing sdfsdfdsf', 1),
(7, 1, 'sfdgdsgdsfdsf', NULL, '', '', 0),
(8, 1, 'sfdgdsgdsfdsf', NULL, '', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `meetings`
--

CREATE TABLE IF NOT EXISTS `meetings` (
  `meeting_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `location` varchar(100) NOT NULL,
  `agenda` varchar(100) NOT NULL,
  `minutes` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `meeting_availability`
--

CREATE TABLE IF NOT EXISTS `meeting_availability` (
  `meeting_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `offeredDateTime` datetime NOT NULL,
  `available` bit(1) NOT NULL,
  PRIMARY KEY (`meeting_id`,`user_id`,`offeredDateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `meeting_availability`
--

INSERT INTO `meeting_availability` (`meeting_id`, `user_id`, `offeredDateTime`, `available`) VALUES
(1, 1, '2011-10-25 02:00:00', b'1'),
(1, 1, '2011-10-26 03:00:00', b'1'),
(1, 1, '2011-10-27 05:00:00', b'0'),
(2, 1, '2011-10-25 05:00:00', b'1'),
(2, 1, '2011-10-25 08:00:00', b'1'),
(2, 1, '2011-10-27 03:00:00', b'0'),
(4, 1, '2011-09-25 14:00:00', b'0'),
(4, 1, '2011-10-26 14:00:00', b'0'),
(4, 1, '2011-10-28 04:00:00', b'0'),
(5, 1, '2011-10-26 00:00:00', b'0'),
(5, 1, '2011-10-27 00:00:00', b'1'),
(6, 1, '2011-10-25 14:00:00', b'0'),
(6, 1, '2011-10-25 19:00:00', b'1'),
(6, 1, '2011-10-28 14:00:00', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `meeting_times`
--

CREATE TABLE IF NOT EXISTS `meeting_times` (
  `meeting_id` int(11) NOT NULL,
  `offeredDateTime` datetime NOT NULL,
  PRIMARY KEY (`meeting_id`,`offeredDateTime`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `meeting_times`
--

INSERT INTO `meeting_times` (`meeting_id`, `offeredDateTime`) VALUES
(1, '2011-10-25 02:00:00'),
(1, '2011-10-26 03:00:00'),
(1, '2011-10-27 05:00:00'),
(2, '2011-10-25 05:00:00'),
(2, '2011-10-25 08:00:00'),
(2, '2011-10-27 03:00:00'),
(4, '2011-09-25 14:00:00'),
(4, '2011-10-26 14:00:00'),
(4, '2011-10-28 04:00:00'),
(5, '2011-10-26 00:00:00'),
(5, '2011-10-27 00:00:00'),
(6, '2011-10-25 14:00:00'),
(6, '2011-10-25 19:00:00'),
(6, '2011-10-28 14:00:00'),
(7, '2011-10-28 00:00:00'),
(7, '2011-10-28 09:00:00'),
(8, '2011-10-28 00:00:00'),
(8, '2011-10-28 09:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `peer_review`
--

CREATE TABLE IF NOT EXISTS `peer_review` (
  `group_id` int(11) NOT NULL,
  `student_from` int(11) NOT NULL,
  `student_for` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`group_id`,`student_from`,`student_for`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `peer_review`
--

INSERT INTO `peer_review` (`group_id`, `student_from`, `student_for`, `score`) VALUES
(1, 1, 3, 7),
(1, 1, 4, 4),
(1, 1, 5, 8);

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE IF NOT EXISTS `projects` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(25) NOT NULL,
  `due_date` date NOT NULL,
  `specification` varchar(100) NOT NULL,
  `total_marks` int(11) NOT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`project_id`, `project_name`, `due_date`, `specification`, `total_marks`) VALUES
(1, 'Assignment 1', '2011-01-22', 'Test', 15),
(2, 'Assignment 2', '2011-11-24', 'This is a test', 10);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE IF NOT EXISTS `tasks` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `title` varchar(25) NOT NULL,
  `description` text NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`task_id`, `group_id`, `title`, `description`, `start_date`, `end_date`, `status`) VALUES
(1, 1, 'Testing Task', 'This is a test', '2011-10-18', '2011-10-28', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `user_type` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `first_name`, `last_name`, `user_type`) VALUES
(1, 'user1', 'password', 'River', 'Tam', 1),
(3, 'user2', 'password', 'John', 'Smith', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
