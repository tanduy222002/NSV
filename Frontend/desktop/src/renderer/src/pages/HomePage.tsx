import { useNavigate } from 'react-router-dom';
import { useAppSelector } from '@renderer/hooks';
import { useEffect } from 'react';

const HomePage = () => {
    const navigate = useNavigate();
    const user = useAppSelector((state) => state.auth.value);

    useEffect(() => {
        if (!user) navigate('/auth/login');
    }, []);

    return <div>Home</div>;
};

export default HomePage;
