import { useNavigate } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { UserInfo } from '@renderer/components';
import partnerIconSrc from '@renderer/assets/partner-icon.png';
import { CreatePartnerForm } from '@renderer/features/partner';

const CreatePartnerPage = () => {
    const navigate = useNavigate();
    const goToPartnerPage = () => navigate('/partner');

    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToPartnerPage}
                />
                <img alt="form-icon" src={partnerIconSrc} />
                <h1 className="text-xl font-semibold">Thêm đối tác</h1>
            </div>
            <CreatePartnerForm />
        </div>
    );
};

export default CreatePartnerPage;
