import { useState } from 'react';

const useProfile = () => {
    const [profile, setProfile] = useState({
        name: 'Manuella Tarly',
        location: 'San Francisco',
        photo: 'https://bootstrapious.com/i/snippets/sn-profile/teacher.jpg',
    });
    
    const [posts, setPosts] = useState([
        'https://bootstrapious.com/i/snippets/sn-profile/img-3.jpg',
        'https://bootstrapious.com/i/snippets/sn-profile/img-4.jpg',
        'https://bootstrapious.com/i/snippets/sn-profile/img-5.jpg',
        'https://bootstrapious.com/i/snippets/sn-profile/img-6.jpg'
    ]);

    const [newPost, setNewPost] = useState('');

    const handleProfileChange = (e) => {
        setProfile({
            ...profile,
            [e.target.name]: e.target.value
        });
    };

    const handlePostChange = (e) => {
        setNewPost(e.target.value);
    };

    const handleAddPost = (e) => {
        e.preventDefault();
        if (newPost) {
            setPosts([...posts, newPost]);
            setNewPost('');
        }
    };

    const handleProfileEdit = (e) => {
        e.preventDefault();
        // Handle profile update logic here
        alert('Profile updated!');
    };

    return {
        profile,
        posts,
        newPost,
        handleProfileChange,
        handlePostChange,
        handleAddPost,
        handleProfileEdit
    };
};

export default useProfile;
