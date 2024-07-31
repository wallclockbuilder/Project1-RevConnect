import React, { useEffect, useState } from 'react';
import Follow from '../components/Follow';
import { useAuth } from '../context/AuthContext';
import { User } from '../interface/types';

const FollowPage: React.FC = () => {
    const { user, token } = useAuth();
    const [usersArray, setUsers] = useState<User[]>([]);

    console.log("Initial users state in FollowPage:", usersArray);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const req = {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                };
                const url = `http://localhost:9090/api/users`;
                const response = await fetch(url, req);
                console.log("Response:", response);
                if (response.ok) {
                    const contentType = response.headers.get("Content-Type");
                    if (contentType && contentType.includes("application/json")) {
                        const data = await response.json();
                        console.log("Fetched data:", data);
                        if (Array.isArray(data)) {
                            setUsers(data);
                        } else {
                            console.error("Fetched data is not an array:", data);
                        }
                    } else {
                        console.error("Expected JSON response but got", contentType);
                    }
                } else {
                    console.error("Failed to fetch users", response.statusText);
                }
            } catch (error) {
                console.error("Failed to fetch users:", error);
            }
        };

        fetchUsers();
    }, [token]);

    useEffect(() => {
        console.log("Users state after fetchUsers is called:", usersArray);
    }, [usersArray]);

    return (
        <div className="follow">
            <h1>Users to Follow</h1>
            <Follow usersArray={usersArray} />
        </div>
    );
};

export default FollowPage;
