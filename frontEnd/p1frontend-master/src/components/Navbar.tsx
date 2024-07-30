import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Navbar, Nav, Dropdown, DropdownButton } from 'react-bootstrap';
import '../css/navbar.css';


const AppNavbar: React.FC = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/');
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
                    <Nav className="ml-auto">
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