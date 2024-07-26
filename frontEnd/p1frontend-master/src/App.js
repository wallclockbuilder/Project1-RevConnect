import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import Signup from "./pages/signup";
import Home from "./pages/Home";
import Profile from "./pages/profile";
import ChatPage from "./pages/ChatPage";
import "bootstrap/dist/css/bootstrap.min.css";
import Signin from "./pages/Signin";
import ChatRoom from "./components/ChatRoom";
import MyPosts from "./pages/MyPosts";


function App() {
    return (
        <div className="App">

            <Routes>
                <Route path="/" element={<Signin />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/home" element={<ProtectedRoute><Home /></ProtectedRoute>} />
                <Route path="/myposts" element={<ProtectedRoute><MyPosts /></ProtectedRoute>} />
                {/* <Route path="/chat/*" element={<ProtectedRoute><ChatPage /></ProtectedRoute>} /> */}
                <Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
            </Routes>

        </div>
    );
}

export default App;
