import { Outlet } from 'react-router-dom';
import AuthBackgroundImage from '../assets/auth-background-image.png';

const AuthPage = () => {
    return (
        <div className="bg-[#7C8DB5] w-screen h-screen relative px-[80px] py-[50px]">
            <div className="w-full h-full flex items-center rounded-md bg-[#EFF4FA]">
                <Outlet />
                <div className="grow-[4] shrink-[4] flex items-center justify-center">
                    <img
                        className="w-9/10 h-9/10"
                        src={AuthBackgroundImage}
                        alt="background image"
                    />
                </div>
            </div>
        </div>
    );
};

export default AuthPage;
