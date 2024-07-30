<<<<<<< HEAD
import React, { useState } from 'react';
=======
import React, {  useState } from 'react';
>>>>>>> 10441f2 ( added back the chatpage and chatroom in the app.js , added search button and input in the navbar)
import config from '../config';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Navbar, Nav, Dropdown, DropdownButton, Form, FormControl, Button } from 'react-bootstrap';
import '../css/navbar.css';

const AppNavbar: React.FC = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const [searchInput, setSearchInput] = useState('');

    const handleLogout = () => {
        logout();
        navigate('/');
    };

<<<<<<< HEAD
    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchInput(e.target.value);
    };

=======
    //search input value here 
    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchInput(e.target.value);
    };
>>>>>>> 10441f2 ( added back the chatpage and chatroom in the app.js , added search button and input in the navbar)
    const handleSearchSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
<<<<<<< HEAD
            const response = await fetch(`${config.BASE_URL}/api/users/username/${searchInput}`, {
                headers: {
                    'Authorization': `Bearer ${user.token}`, // Assuming user.token holds the token
                },
                credentials: 'include',
            });
=======
            // Replace with your actual API endpoint and logic to fetch user ID by username
            const response = await fetch(`${config.BASE_URL}/api/getUserIdByUsername?username=${searchInput}`);
>>>>>>> 10441f2 ( added back the chatpage and chatroom in the app.js , added search button and input in the navbar)

            if (!response.ok) {
                throw new Error('User not found');
            }

<<<<<<< HEAD
            const userDetails = await response.json();
            const { userId } = userDetails;
            console.log(user);

            navigate(`/profile/${userId}`);
        } catch (error) {
            console.error('Error fetching user details:', error);
            // Handle error (e.g., display a notification)
        }
    };

=======
            const { userId } = await response.json();

            // Fetch user details using userId
            const userDetailsResponse = await fetch(`${config.BASE_URL}/api/getUserDetailsById?userId=${userId}`);
            if (!userDetailsResponse.ok) {
                throw new Error('User details not found');
            }

            const userDetails = await userDetailsResponse.json();
            console.log(userDetails);

            // Navigate to the user's profile page (assuming you have a route for it)
            navigate(`/profile/${userId}`);
        } catch (error) {
            console.error(error);
            // Handle error (e.g., display a notification)
        }
    };
>>>>>>> 10441f2 ( added back the chatpage and chatroom in the app.js , added search button and input in the navbar)
    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Navbar.Brand as={Link} to="/Home">RevatureConnect</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link as={Link} to="/chat">Chat</Nav.Link>
                    <Nav.Link as={Link} to="/connections">Connections</Nav.Link>
                    <Nav.Link as={Link} to="/follow">Follow</Nav.Link>
                </Nav>
                {user ? (
                    <Nav className="ms-auto">
<<<<<<< HEAD
                        <Form className="d-flex me-auto search-form" onSubmit={handleSearchSubmit}>
=======
                        <Form className="d-flex me-auto search-form">
>>>>>>> 10441f2 ( added back the chatpage and chatroom in the app.js , added search button and input in the navbar)
                            <FormControl 
                                type="search"
                                placeholder="Search"
                                className="mr-2 search-input"
                                aria-label="Search"
<<<<<<< HEAD
                                value={searchInput}
                                onChange={handleSearchChange}
                            />
                            <Button type="submit" className="search-button">Search</Button>
=======
                            />
                            <Button className="search-button">Search</Button>
>>>>>>> 10441f2 ( added back the chatpage and chatroom in the app.js , added search button and input in the navbar)
                        </Form>
                        <DropdownButton title={user.username} id="dropdown-menu-align-right" className="dropdown-menu-end">
                            <Dropdown.Item as={Link} to="/profile">Profile</Dropdown.Item>
                            <Dropdown.Item onClick={handleLogout}>Logout</Dropdown.Item>
                        </DropdownButton>
                    </Nav>
                ) : (
                    <Nav className="ml-auto">
                        <Nav.Link as={Link} to="/signin">Sign In</Nav.Link>
                        <Nav.Link as={Link} to="/signup">Sign Up</Nav.Link>
                    </Nav>
                )}
            </Navbar.Collapse>
        </Navbar>
    );
};

export default AppNavbar;
