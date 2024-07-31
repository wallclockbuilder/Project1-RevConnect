import React, { useEffect, useState } from 'react';
import Follow from '../components/Follow';
import Followee from '../components/Followee';
import Followers from '../components/Followers';
import '../css/FollowPage.css';
import { FollowDTO, User } from '../interface/types';
import { useAuth } from '../context/AuthContext';



const sampleFolloweesArray: User[] = [
    { userId: 4, username: 'michael_scott', email: 'michael@example.com', firstName: 'Michael', lastName: 'Scott' },
    { userId: 5, username: 'pam_beesly', email: 'pam@example.com', firstName: 'Pam', lastName: 'Beesly' },
    { userId: 6, username: 'jim_halpert', email: 'jim@example.com', firstName: 'Jim', lastName: 'Halpert' }
];

const FollowPage: React.FC = () => {
    const [followees, setFollowees] = useState<User[]>(sampleFolloweesArray);
    const {token, user } = useAuth();

    useEffect(() => {
        const fetchFollowees = async () => {
            try {
                const url = `http://localhost:9090/api/users/${user.userId}/followees`;
                const response = await fetch(url, {
                    method: "GET",
                    credentials: 'include',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                if (response.ok) {
                    const contentType = response.headers.get("Content-Type");
                    if (contentType && contentType.includes("application/json")) {
                        const data = await response.json();
                        if (Array.isArray(data)) {
                            setFollowees(data);
                        } else {
                            console.error("Fetched data is not an array:", data);
                        }
                    } else {
                        console.error("Expected JSON response but got", contentType);
                    }
                } else {
                    console.error("Failed to fetch followees", response.statusText);
                }
            } catch (error) {
                console.error("Failed to fetch followees:", error);
            }
        };

        fetchFollowees();
    }, [user.userId, token]);

    useEffect(() => {
        setFollowees(sampleFolloweesArray);
    }, []);


    const handleFollow = async (followerUserId: number, followeeUserId: number) => {
        try {
            const followDTO: FollowDTO = {
                followerUserId: followerUserId,
                followeeUserId: followeeUserId
            };
            const url = `http://localhost:9090/api/users/${followeeUserId}/followers`;
            const response = await fetch(url, {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(followDTO)
            });

            if (response.ok) {
                setFollowees(followees.filter((user: User) => user.userId !== followeeUserId));
                console.log(`Followed user with userId: ${followeeUserId}`);
            } else {
                console.error("Failed to follow user", response.statusText);
            }
        } catch (error) {
            console.error("Failed to follow user:", error);
        }
    };

    return (
        <div className="follow-page">
            <div className="follow-column">
                <h2>Users to Follow</h2>
                <Follow onFollow = {handleFollow}/>
            </div>
            <div className="follow-column">
                <h2>Users I Follow</h2>
                <Followee followees={followees}/>
            </div>
            <div className="follow-column">
                <h2>Users Who Follow Me</h2>
                <Followers />
            </div>
        </div>
    );
};

export default FollowPage;
