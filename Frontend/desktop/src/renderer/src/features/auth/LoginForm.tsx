import TextInput from './components/TextInput';
import PasswordInput from './components/PasswordInput';
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
    const navigate = useNavigate();
    const goToRegisterForm = () => navigate('/register');
    const goToForgotPasswordForm = () => navigate('/forgot-password');
    const handleLogin = () => {
        navigate('/');
    };

    return (
        <form
            className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4"
            onSubmit={() => handleLogin()}
        >
            <h1 className="text-xl font-semibold text-[#1A3389]">
                Giải pháp quản lý kho <br /> nông sản thông minh
            </h1>
            <p className="text-sm text-[#7C8DB5] my-2">
                Vui lòng đăng nhập để sử dụng các tính năng
            </p>
            <TextInput label="Tên đăng nhập" placeHolder="Tên đăng nhập" />
            <PasswordInput label="Mật khẩu" placeHolder="Mật khẩu" />

            <div className="flex items-center justify-between mt-3">
                <div className="flex items-center gap-1 text-sm outline-none text-[#7C8DB5]">
                    <input type="checkbox" />
                    <p>Ghi nhớ đăng nhập</p>
                </div>
                <div
                    className="text-sm underline text-[#7C8DB5] cursor-pointer"
                    onClick={goToForgotPasswordForm}
                >
                    Quên mật khẩu ?
                </div>
            </div>
            <div className="flex items-center gap-2 mt-4">
                <button
                    className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                    type="submit"
                >
                    Đăng nhập
                </button>
                <button
                    className="border border-[#1A3389] rounded-md px-4 py-2 text-[#1A3389] font-semibold w-full"
                    type="button"
                    onClick={goToRegisterForm}
                >
                    Đăng ký
                </button>
            </div>
        </form>
    );
};

export default LoginForm;
