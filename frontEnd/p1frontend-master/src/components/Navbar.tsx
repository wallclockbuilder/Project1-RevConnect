import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Navbar, Nav, Dropdown, DropdownButton } from 'react-bootstrap';

const AppNavbar: React.FC = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Navbar.Brand as={Link} to="/">MyApp</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link as={Link} to="/">Home</Nav.Link>
                    <Nav.Link as={Link} to="/myposts">My Posts</Nav.Link>
                    <Nav.Link as={Link} to="/chat">Chat</Nav.Link>
                    <Nav.Link as={Link} to="/connections">Connections</Nav.Link>
                    <Nav.Link as={Link} to="/follow">Follow</Nav.Link>
                </Nav>
                {user ? (
                    <DropdownButton title={user.username} id="dropdown-menu-align-right">
                        <Dropdown.Item as={Link} to="/profile">Profile</Dropdown.Item>
                        <Dropdown.Item onClick={handleLogout}>Logout</Dropdown.Item>
                    </DropdownButton>
                ) : (
                    <Nav>
                        <Nav.Link as={Link} to="/signin">Sign In</Nav.Link>
                        <Nav.Link as={Link} to="/signup">Sign Up</Nav.Link>
                    </Nav>
                )}
            </Navbar.Collapse>
        </Navbar>
    );
};

export default AppNavbar;
