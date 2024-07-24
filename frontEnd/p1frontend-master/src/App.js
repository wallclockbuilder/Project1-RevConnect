import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Signin from "./pages/signin";
import Signup from "./pages/signup";
import "bootstrap/dist/css/bootstrap.min.css";
import Profile from "./pages/profile";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Signin />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/profile" element={<Profile />} />
            </Routes>
        </Router>
    );
}

export default App;
