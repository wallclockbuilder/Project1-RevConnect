import React, { useEffect } from "react";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import ProtectedRoute from "./ProtectedRoute";
import Signup from "./pages/signup";
import Home from "./pages/Home";
import Profile from "./pages/profile";
import Signin from "./pages/Signin";
import MyPosts from "./pages/MyPosts";
import AppNavbar from "./components/Navbar";
import ChatPage from "./pages/ChatPage";
import ChatRoom from "./components/ChatRoom";
import AdminPage from "./pages/AdminPage";
import Follow from "./pages/FollowPage";





const App: React.FC = () => {

    const location = useLocation();
    const navigate = useNavigate();

    const isAuthPage = location.pathname === "/" || location.pathname === "/signup";

    useEffect(() => {
        // Save the current path to local storage
        localStorage.setItem('lastPath', location.pathname);
    }, [location]);

    useEffect(() => {
        // On initial load, navigate to the last saved path
        const lastPath = localStorage.getItem('lastPath');
        if (lastPath) {
            navigate(lastPath);
        }
    }, [navigate]);

    return (
        <div className="App">
            {!isAuthPage && <AppNavbar />}
            <Routes>
                <Route path="/" element={<Signin />} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/home" element={<ProtectedRoute><Home /></ProtectedRoute>} />
                <Route path="/myposts" element={<ProtectedRoute><MyPosts /></ProtectedRoute>} />
                <Route path="/chat" element={<ProtectedRoute><ChatPage /></ProtectedRoute>} />
                <Route path="/chat/:userId" element={<ProtectedRoute><ChatRoom /></ProtectedRoute>} />
                <Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
                <Route path="/profile/:userId" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
                <Route path="/follow" element={<ProtectedRoute><Follow /></ProtectedRoute>} />
                <Route path="/AdminPage" element={<AdminPage />} />

            </Routes>
        </div>
    );
}


export default App;
