import { ProductList } from '@renderer/features/product';
import { Outlet } from 'react-router-dom';
import fruitSrc from '../assets/fruit.png';

const ProductPage = () => {
    return (
        <div className="w-full h-screen px-10 py-10">
            <h1 className="flex items-center gap-2 font-semibold text-xl my-5 ">
                <img src={fruitSrc} /> <p>Sản phẩm</p>
            </h1>
            <div className=" flex justify-between">
                <ProductList />
                <Outlet />
            </div>
        </div>
    );
};

export default ProductPage;
