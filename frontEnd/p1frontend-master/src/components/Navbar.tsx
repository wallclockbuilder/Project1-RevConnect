import React, { useState } from 'react';
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

    const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchInput(e.target.value);
    };

    const handleSearchSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await fetch(`${config.BASE_URL}/api/users/username/${searchInput}`, {
                headers: {
                    'Authorization': `Bearer ${user.token}`, // Assuming user.token holds the token
                },
                credentials: 'include',
            });

            if (!response.ok) {
                throw new Error('User not found');
            }

            const userDetails = await response.json();
            const { userId } = userDetails;
            console.log(user);

            navigate(`/profile/${userId}`);
        } catch (error) {
            console.error('Error fetching user details:', error);
            // Handle error (e.g., display a notification)
        }
    };

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
                        <Form className="d-flex me-auto search-form" onSubmit={handleSearchSubmit}>
                            <FormControl 
                                type="search"
                                placeholder="Search"
                                className="mr-2 search-input"
                                aria-label="Search"
                                value={searchInput}
                                onChange={handleSearchChange}
                            />
                            <Button type="submit" className="search-button">Search</Button>
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