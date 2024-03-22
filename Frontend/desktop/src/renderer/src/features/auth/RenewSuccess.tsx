import { useNavigate } from 'react-router-dom';
import { MdCheckCircleOutline } from 'react-icons/md';
import { Button } from '@renderer/components';

const RenewSuccess = () => {
    const navigate = useNavigate();
    const goToLoginForm = () => navigate('/auth/login');
    return (
        <div className="grow-[3] shrink-[3] h-full bg-white px-[50px] py-[50px] flex flex-col items-center gap-4">
            <h1 className="text-xl font-semibold text-[#1A3389]">
                Tạo mới thành công!
            </h1>
            <p className="text-center text-base text-[#1A3389] font-semibold">
                Mật khẩu mới đã được cập nhật <br /> Hãy đăng nhập lại bằng mật
                khẩu mới
            </p>
            <MdCheckCircleOutline className="text-green-600 w-[180px] h-[180px]" />
            <Button
                className="bg-[#1A3389] rounded-md px-4 py-2 text-white font-semibold w-full"
                action={goToLoginForm}
                text="Quay lại trang đăng nhập"
            />
        </div>
    );
};

export default RenewSuccess;
