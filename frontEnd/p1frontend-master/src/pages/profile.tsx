import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/profile.css'; // Optional: If you want to include custom styles
import { Helmet } from 'react-helmet';
import { useAuth } from '../context/AuthContext';
import config from '../config';
import { Post as PostType, Comment as CommentType, Like as LikeType } from '../interface/types';

const formatDate = (dateString: string) => {
    const options: Intl.DateTimeFormatOptions = {
        weekday: 'long',
        day: '2-digit',
        month: '2-digit',
    };
    const date = new Date(dateString);
    return date.toLocaleDateString('en-GB', options); // Format as "Day, Date/Month"
};

const Profile: React.FC = () => {
    const [posts, setPosts] = useState<PostType[]>([]);
    const [comments, setComments] = useState<CommentType[]>([]);
    const [likes, setLikes] = useState<LikeType[]>([]);
    const [singlePostContents, setSinglePostContents] = useState<string[]>([]);
    const { user, token, logout } = useAuth();
    const [postDates, setPostDates] = useState<string[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const postsResponse = await fetch(`${config.BASE_URL}/api/posts`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    },
                    credentials: 'include'
                });
                const postsData = await postsResponse.json();
    
                // Filter posts for the current user
                const userPosts = postsData.filter((post: PostType) => post.userId === user?.userId);
    
                // Set the posts state with userPosts
                setPosts(userPosts);
    
                // Set the singlePostContents and createdAt separately for table
                const contents = userPosts.map((post: PostType) => post.content);
                const dates = userPosts.map((post: PostType) => formatDate(post.createdAt)); // Format dates
    
                setSinglePostContents(contents);
                setPostDates(dates); // Set formatted dates
    
                const commentsResponse = await fetch(`${config.BASE_URL}/api/comments`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    },
                    credentials: 'include'
                });
                const commentsData = await commentsResponse.json();
                setComments(commentsData);
    
                const likesResponse = await fetch(`${config.BASE_URL}/api/likes`, {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    },
                    credentials: 'include'
                });
                const likesData = await likesResponse.json();
                setLikes(likesData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };
    
        fetchData();
    }, [user, token]);

    return (
        <div id='profileBody'>
            <Helmet>
                <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" />
            </Helmet>
    
            <div className="container bootstrap snippets bootdeys">
                <div className="row" id="user-profile">
                    <div className="col-lg-3 col-md-4 col-sm-4">
                        <div className="main-box clearfix">
                            <h2>{user.firstName} {user.lastName}</h2>
                            <div className="profile-status">
                                <i className="fa fa-check-circle"></i> Online
                            </div>
                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="Profile" className="profile-img img-responsive center-block" />
    
                            <div className="profile-details">
                                <ul className="fa-ul">
                                    <li><i className="fa-li fa fa-comment"></i>Posts: <span>{singlePostContents.length}</span></li>
                                </ul>
                            </div>
    
                            <div className="profile-message-btn center-block text-center">
                                <a href="#" className="btn btn-success">
                                    <i className="fa fa-envelope"></i> Send message
                                </a>
                            </div>
                        </div>
                    </div>
    
                    <div className="col-lg-9 col-md-8 col-sm-8">
                        <div className="main-box clearfix">
                            <div className="profile-header">
                                <h3><span>User info</span></h3>
                                <a href="#" className="btn btn-primary edit-profile">
                                    <i className="fa fa-pencil-square fa-lg"></i> Edit profile
                                </a>
                            </div>
    
                            <div className="row profile-user-info">
                                <div className="col-sm-8">
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            First Name
                                        </div>
                                        <div className="profile-user-details-value">
                                            {user.firstName}
                                        </div>
                                    </div>
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            Last Name
                                        </div>
                                        <div className="profile-user-details-value">
                                            {user.lastName}
                                        </div>
                                    </div>
                                    <div className="profile-user-details clearfix">
                                        <div className="profile-user-details-label">
                                            Email
                                        </div>
                                        <div className="profile-user-details-value">
                                            {user.email}
                                        </div>
                                    </div>
                                </div>
                                <div className="col-sm-4 ">
                                    <div className='profile-bio'>
                                        My Bio:
                                    </div>
                                    <div className='profile-user-details-value'>
                                        {user.bio}
                                    </div>
                                </div>
                            </div>
    
                            <div className="tabs-wrapper profile-tabs">
                                <ul className="nav nav-tabs">
                                    <li className="nav-item">
                                        <a className="nav-link active" href="#tab-posts" data-bs-toggle="tab">posts</a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link" href="#tab-followers" data-bs-toggle="tab">followers</a>
                                    </li>
                                </ul>
    
                                <div className="tab-content">
                                    <div className="tab-pane fade show active" id="tab-posts">
                                        <div className="table-responsive">
                                            <table className="table">
                                                <tbody>
                                                    {singlePostContents.length > 0 ? (
                                                        singlePostContents.map((content, index) => (
                                                            <tr key={index}>
                                                                <td className="text-center">
                                                                    <i className="fa fa-comment"></i>
                                                                </td>
                                                                <td>
                                                                    <p>{content}</p>
                                                                </td>
                                                                <td>
                                                                    {postDates[index] || 'No date available'}
                                                                </td>
                                                            </tr>
                                                        ))
                                                    ) : (
                                                        <tr>
                                                            <td colSpan={3} className="text-center">
                                                                No posts available.
                                                            </td>
                                                        </tr>
                                                    )}
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
    
                                    <div className="tab-pane fade" id="tab-followers">
                                        <ul className="widget-users row">
                                            <li className="col-md-6">
                                                <div className="img">
                                                    <img src="https://bootdey.com/img/Content/avatar/avatar1.png" className="img-responsive" alt="Friend" />
                                                </div>
                                                <div className="details">
                                                    <div className="name">
                                                        <a href="#">John Doe</a>
                                                    </div>
                                                    <div className="time">
                                                        <i className="fa fa-clock-o"></i> Last online: 5 minutes ago
                                                    </div>
                                                    <div className="type">
                                                        <span className="badge bg-danger">Admin</span>
                                                    </div>
                                                </div>
                                            </li>
                                            {/* Add more followers list items as needed */}
                                        </ul>
                                        <br />
                                        <a href="#" className="btn btn-success float-end">View all users</a>
                                    </div>
    
                                    <div className="tab-pane fade" id="tab-chat">
                                        <div className="conversation-wrapper">
                                            <div className="conversation-content">
                                                <div className="conversation-inner" style={{ height: '340px', overflowY: 'scroll' }}>
                                                    <div className="conversation-item item-left clearfix">
                                                        <div className="conversation-user">
                                                            <img src="https://bootdey.com/img/Content/avatar/avatar1.png" className="img-responsive" alt="User" />
                                                        </div>
                                                        <div className="conversation-body">
                                                            <div className="name">
                                                                Ryan Gossling
                                                            </div>
                                                            <div className="time">
                                                                September 21, 2013 18:28
                                                            </div>
                                                            <div className="text">
                                                                I don't think they tried to market it to the billionaire, spelunking, base-jumping crowd.
                                                            </div>
                                                        </div>
                                                    </div>
                                                    {/* Add more chat items as needed */}
                                                </div>
                                            </div>
                                            <div className="conversation-new-message">
                                                <form>
                                                    {/* <div className="form-group">
                                                        <textarea className="form-control" rows="2" placeholder="Enter your message..."></textarea>
                                                    </div> */}
                                                    <div className="clearfix">
                                                        <button type="submit" className="btn btn-success float-end">Send message</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
    
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile;
