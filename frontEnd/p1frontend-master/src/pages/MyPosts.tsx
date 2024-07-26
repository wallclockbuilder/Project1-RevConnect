import React, { useEffect, useState } from 'react';
import Post from '../components/Post';
import config from '../config';
import { Post as PostType, Comment as CommentType, Like as LikeType } from '../types';
import { useAuth } from '../context/AuthContext';

const MyPost: React.FC = () => {
    const [posts, setPosts] = useState<PostType[]>([]);
    const [comments, setComments] = useState<CommentType[]>([]);
    const [likes, setLikes] = useState<LikeType[]>([]);
    const { user, token } = useAuth();

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
                setPosts(postsData.filter((post: PostType) => post.userId === user?.userId));

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
        <div className="my-posts">
            <h1>My Posts</h1>
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

export default MyPost;
