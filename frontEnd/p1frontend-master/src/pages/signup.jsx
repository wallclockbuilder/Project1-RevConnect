import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/signup.css'; 
import logo from '../pictures/logo.png';
import { useNavigate } from 'react-router-dom';

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [email, setEmail] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [bio, setBio] = useState('');
    const navigate = useNavigate(); // Initialize useNavigate

    const submitSignUp = async (e) => {
        e.preventDefault();
        
        if (password !== confirmPassword) {
            alert("Passwords do not match!");
            return;
        }

        const url = "http://localhost:9090/api/users";
        const dto = {
            username: username,
            password: password,
            email: email,
            firstName: firstName,
            lastName: lastName,
            bio: bio
        };
        const headers = {
            "Content-Type": "application/json"
        };
        const req = {
            method: "POST",
            body: JSON.stringify(dto),
            headers: headers
        };

        try {
            const response = await fetch(url, req);
            const responseBody = await response.json();
            console.log(responseBody);
            // Handle the response (e.g., show a success message, redirect, etc.)
            navigate('/');
        } catch (error) {
            console.error("Error during sign-up:", error);
            // Handle errors (e.g., show an error message)
        }
    };

    return (
        <div className="container">
            <div className="register-container">
                <div className="register-logo">
                    <img id='logo' src={logo} alt="Logo" width="150" height="150" />
                </div>
                <div className="register-form">
                    <form onSubmit={submitSignUp}>
                        <h1 className="h3 mb-3 font-weight-normal text-center">Register</h1>
                        
                        <label htmlFor="firstName" className="sr-only">First Name</label>
                        <input 
                            type="text" 
                            id="firstName" 
                            className="form-control" 
                            placeholder="First Name" 
                            required 
                            value={firstName} 
                            onChange={(e) => setFirstName(e.target.value)} 
                        />
                        
                        <label htmlFor="lastName" className="sr-only">Last Name</label>
                        <input 
                            type="text" 
                            id="lastName" 
                            className="form-control" 
                            placeholder="Last Name" 
                            required 
                            value={lastName} 
                            onChange={(e) => setLastName(e.target.value)} 
                        />
                        
                        <label htmlFor="username" className="sr-only">Username</label>
                        <input 
                            type="text" 
                            id="username" 
                            className="form-control" 
                            placeholder="Username" 
                            required 
                            autoFocus 
                            value={username} 
                            onChange={(e) => setUsername(e.target.value)} 
                        />
                        
                        <label htmlFor="inputEmail" className="sr-only">Email address</label>
                        <input 
                            type="email" 
                            id="inputEmail" 
                            className="form-control" 
                            placeholder="Email address" 
                            required 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                        />
                        
                        <label htmlFor="inputPassword" className="sr-only">Password</label>
                        <input 
                            type="password" 
                            id="inputPassword" 
                            className="form-control" 
                            placeholder="Password" 
                            required 
                            value={password} 
                            onChange={(e) => setPassword(e.target.value)} 
                        />
                        
                        <label htmlFor="confirmPassword" className="sr-only">Confirm Password</label>
                        <input 
                            type="password" 
                            id="confirmPassword" 
                            className="form-control" 
                            placeholder="Confirm Password" 
                            required 
                            value={confirmPassword} 
                            onChange={(e) => setConfirmPassword(e.target.value)} 
                        />
                        
                        <label htmlFor="bio" className="sr-only">Bio</label>
                        <textarea 
                            id="bio" 
                            className="form-control" 
                            placeholder="Tell us about yourself" 
                            rows="3" 
                            value={bio} 
                            onChange={(e) => setBio(e.target.value)} 
                        ></textarea>
                        
                        <button className="btn btn-primary btn-block" type="submit">Register</button>
                        <p className="mt-4 text-muted text-center">© 2024</p>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Register;
