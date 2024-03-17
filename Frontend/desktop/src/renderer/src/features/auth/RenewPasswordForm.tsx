import { useNavigate } from 'react-router-dom';
import PasswordInput from './components/PasswordInput';
import { useEffect, useState } from 'react';
import TextInput from './components/TextInput';

const RenewPasswordForm = () => {
    const navigate = useNavigate();
    const [activeTime, setActiveTime] = useState(0);

    const goToRenewPasswordSuccess = () => navigate('/renew-success');
    const goToLoginForm = () => navigate('/login');
    const setRenewPasswordDuration = () => setActiveTime(10);
    const handleRenewPassword = () => {
        goToRenewPasswordSuccess();
    };

    useEffect(() => {
        const activeTimeout = setTimeout(
            () => setActiveTime((prev) => (prev > 0 ? prev - 1 : 0)),
            1000
        );
        () => clearTimeout(activeTimeout);
    }, [activeTime]);
    return (
        <form
            className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col gap-4"
            onSubmit={() => handleRenewPassword()}
        >
            <p>{activeTime}</p>
            <h1 className="text-xl font-semibold text-[#1A3389]">
                Xác nhận mật khẩu mới
            </h1>

            <PasswordInput label="Mật khẩu mới" placeHolder="Mật khẩu mới" />
            <PasswordInput
                label="Xác nhận mật khẩu"
                placeHolder="Xác nhận mật khẩu"
            />
            {activeTime > 0 && (
                <TextInput
                    label="otp"
                    placeHolder="Nhập OTP đã gửi đến email của bạn"
                />
            )}

            {activeTime > 0 ? (
                <button
                    className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                    type="button"
                    onClick={goToRenewPasswordSuccess}
                >
                    Xác nhận
                </button>
            ) : (
                <button
                    className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                    type="button"
                    onClick={setRenewPasswordDuration}
                >
                    Tạo mới mật khẩu
                </button>
            )}
            <button
                className="border border-[#1A3389] rounded-md px-4 py-2 text-[#1A3389] font-semibold w-full"
                type="button"
                onClick={goToLoginForm}
            >
                Quay lại
            </button>
        </form>
    );
};

export default RenewPasswordForm;
