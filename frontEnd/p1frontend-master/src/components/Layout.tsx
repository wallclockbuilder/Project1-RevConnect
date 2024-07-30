import { Outlet } from "react-router-dom"
import AppNavbar from "./Navbar"
import React from 'react';

const Layout: React.FC = () => {
    return (
        <div>
            <AppNavbar/>
            <div className="content">
                <Outlet/>
            </div>
        </div>
    );
};

export default Layout;