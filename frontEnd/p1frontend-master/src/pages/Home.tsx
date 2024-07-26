import React, { useEffect, useState } from 'react';
import Post from '../components/Post';
import config from '../config';
import { Post as PostType, Comment as CommentType, Like as LikeType } from '../types';
import AppNavbar from '../components/Navbar';
const Home: React.FC = () => {
    const [posts, setPosts] = useState<PostType[]>([]);
    const [comments, setComments] = useState<CommentType[]>([]);
    const [likes, setLikes] = useState<LikeType[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const postsResponse = await fetch(`${config.BASE_URL}/api/posts`, { credentials: 'include' });
                const postsData = await postsResponse.json();
                setPosts(postsData);

                const commentsResponse = await fetch(`${config.BASE_URL}/api/comments`, { credentials: 'include' });
                const commentsData = await commentsResponse.json();
                setComments(commentsData);

                const likesResponse = await fetch(`${config.BASE_URL}/api/likes`, { credentials: 'include' });
                const likesData = await likesResponse.json();
                setLikes(likesData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="home">
            <AppNavbar />
            <h1>Home</h1>
            {posts.map((post) => (
                <Post
                    key={post.postId}
                    post={post}
                    comments={comments.filter(comment => comment.postId === post.postId)}
                    likes={likes.filter(like => like.postId === post.postId)}
                />
            ))}
        </div>
    );
};

export default Home;
