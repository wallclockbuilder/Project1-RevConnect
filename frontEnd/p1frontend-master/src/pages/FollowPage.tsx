import React, { useEffect, useState } from 'react';
import Follow from '../components/Follow';
import Followers from '../components/Followers';
import '../css/FollowPage.css';
import { FollowDTO, User } from '../interface/types';
import { useAuth } from '../context/AuthContext';
import Followee from '../components/Followees';



const samplefollowedUsersArray: User[] = [
    { userId: 4, username: 'michael_scott', email: 'michael@example.com', firstName: 'Michael', lastName: 'Scott', active: true , admin: false},
    { userId: 5, username: 'pam_beesly', email: 'pam@example.com', firstName: 'Pam', lastName: 'Beesly' , active: true , admin: false},
    { userId: 6, username: 'jim_halpert', email: 'jim@example.com', firstName: 'Jim', lastName: 'Halpert' , active: true , admin: false}
];

const FollowPage: React.FC = () => {
    const { user, token } = useAuth();
    const [usersToFollow, setUsersToFollow] = useState<User[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [followedUsers, setFollowedUsers] = useState<User[]>([]);
    const [followers, setFollowers] = useState<User[]>([]);
    const [refresh, setRefresh] = useState<boolean>(false);
    const [loadingUsersToFollow, setLoadingUsersToFollow] = useState<boolean>(true);
    const [loadingFollowedUsers, setLoadingFollowedUsers] = useState<boolean>(true);
    const [loadingFollowers, setLoadingFollowers] = useState<boolean>(true);

    console.log("Initial usersToFollow state in FollowPage:", followedUsers);


    useEffect(() => {
        const fetchUsersToFollow = async () => {
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
                        console.log("Fetched usersToFollow data:", data);
                        if (Array.isArray(data)) {
                            setUsersToFollow(data.filter((userToFollow: User) => user && userToFollow.userId !== user.userId));
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
            } finally {
                setLoadingUsersToFollow(false);
            }
        };

        fetchUsersToFollow();
    }, [user, token]);


    useEffect(() => {
        const fetchfollowedUsers = async () => {
            try {
                if (user && user.userId) {
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
                        console.log("Content type:", contentType);
                        console.log("Fetched FollowedUsers data: ", response);
                        if (contentType && contentType.includes("application/json")) {
                            const data = await response.json();
                            if (Array.isArray(data)) {
                                setFollowedUsers(data);
                            } else {
                                console.error("Fetched data is not an array:", data);
                            }
                        } else {
                            console.error("Expected JSON response but got", contentType);
                        }
                    } else {
                        console.error("Failed to fetch followedUsers", response.statusText);
                    }
                }
            } catch (error) {
                console.error("Failed to fetch followedUsers:", error);
            } finally {
                setLoadingFollowedUsers(false);
            }
        };
    
        fetchfollowedUsers();
    }, [user, token, refresh]);

    const handleFollow = async (followerUserId: number, followeeUserId: number) => {
        try {
            console.log(`-=-=-=-=-=-=-=-=-=-=before-=-=-=-=-=-=-=-=-=-=-=-=-=-`);
            console.log(`Users to follow: ${usersToFollow}`);
            console.log(`Followed users: ${followedUsers}`);
            console.log(`Follower userId: ${followerUserId}`);
            console.log(`followedUser userId: ${followeeUserId}`);
            console.log(`-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-`);
            const followedUser: User | undefined = usersToFollow.find((userToFollow: User) => userToFollow.userId === followeeUserId);
            console.log(`Followed user: ${followedUser}`);
            if (!followedUser) {
                console.error(`User with userId: ${followeeUserId} not found in usersToFollow`);
                return;
            } else if (followedUser){
                //update frontend state
                setFollowedUsers([...followedUsers, followedUser]);
                const usersToFollowithoutFollowedUser = usersToFollow.filter((userToFollow: User) => userToFollow.userId !== followeeUserId);
                setUsersToFollow(usersToFollowithoutFollowedUser);
            }
            console.log(`-=-=-=-=-=-=-=-=-=-=-=after-=-=-=-=-=-=-=-=-=-=-=-=-`);
            console.log(`Users to follow: ${usersToFollow}`);
            console.log(`Followed user: ${followedUser}`);
            console.log(`Followed users: ${followedUsers}`);
            console.log(`-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-`);

            const followDTO: FollowDTO = {
                followerUserId: followerUserId,
                followeeUserId: followeeUserId
            };
            //update the database
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
                console.log(`Fetched data after following user: ${response}`);
                setRefresh(true);
                console.log(`Followed user with userId: ${followeeUserId}`);
            } else {
                console.error("Failed to follow user", response.statusText);
            }
        } catch (error) {
            console.error("Failed to follow user:", error);
        }
    };


    useEffect(() => {
        const fetchFollowers = async () => {
            try {
                if (user !== null) {
                    const url = `http://localhost:9090/api/users/${user.userId}/followers`;
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
                        console.log("Content type:", contentType);
                        console.log("fetched followers Response:", response);
                        if (contentType && contentType.includes("application/json")) {
                            const data = await response.json();
                            if (Array.isArray(data)) {
                                setFollowers(data);
                            } else {
                                console.error("Fetched data is not an array:", data);
                            }
                        } else {
                            console.error("Expected JSON response but got", contentType);
                        }
                    } else {
                        console.error("Failed to fetch followers", response.statusText);
                    }
                }
            } catch (error) {
                console.error("Failed to fetch followers:", error);
            } finally {
                setLoadingFollowers(false);
            }
        };
    
        fetchFollowers();
    }, [user, token, refresh]);



    return (
        <div className="follow-page">
            <div className="follow-column">
                <h2>Users to Follow</h2>
                <Follow onFollow = {handleFollow} usersToFollow = {usersToFollow} loading = {loadingUsersToFollow}/>
            </div>
            <div className="follow-column">
                <h2>Followed Users</h2>
                <Followee followedUsers={followedUsers} loading = {loadingFollowedUsers}/>
            </div>
            <div className="follow-column">
                <h2>Followers</h2>
                <Followers followers = {followers} loading = {loadingFollowers}/>
            </div>
        </div>
    );
};

export default FollowPage;
