import { CreateProductForm } from '@renderer/features/product';
import { IoChevronBack } from 'react-icons/io5';
import fruitIconSrc from '@renderer/assets/fruit.png';
import { UserInfo } from '@renderer/components';
import { useNavigate } from 'react-router-dom';

const CreateProductPage = () => {
    const navigate = useNavigate();
    const goToProductPage = () => navigate('/product');
    return (
        <div className=" w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2 mb-5">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToProductPage}
                />
                <img src={fruitIconSrc} />
                <h1 className="font-semibold text-xl">Thêm sản phẩm</h1>
            </div>

            <CreateProductForm />
        </div>
    );
};

export default CreateProductPage;
