import { useRef, useState } from 'react';
import {
    FormInput,
    Button,
    AsyncSelectInput,
    FileInput
} from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { getProductType } from '@renderer/services/api';
import ProductCategoryPicker from './ProductCategoryPicker';
import { ProductCategory } from '@renderer/types/product';
import { productDefaultValue } from '@renderer/constants/product';
import DropdownContainer from './DropdownContainer';
import ProductCategoryList from './ProductCategoryList';
import { createProduct } from '@renderer/services/api';
import { GiFruitBowl } from 'react-icons/gi';

const CreateProductForm = () => {
    const nameRef = useRef<HTMLInputElement>(null);
    const [product, setProduct] = useState(productDefaultValue);
    const updateProductVariety = (variety: string) => {
        setProduct((prev) => ({ ...prev, variety: variety }));
    };
    const deleteProductCategory = (index: number) => {
        setProduct((prev) => ({
            ...prev,
            categories: prev.categories.filter((_, i) => i !== index)
        }));
    };

    const [fileSrc, setFileSrc] = useState<string | undefined>();

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const getProductTypeCallback = async () => {
        return await getProductType({ token: accessToken });
    };

    const handleFileChange = (fileSrc: string) => {
        setFileSrc(fileSrc);
    };

    const addCategory = (category: ProductCategory) => {
        setProduct((prev) => ({
            ...prev,
            categories: [...prev.categories, category]
        }));
    };

    const saveProduct = async (e) => {
        e.preventDefault();
        const response = await createProduct({
            token: accessToken,
            product: {
                ...product,
                name: nameRef?.current?.value ?? '',
                image: fileSrc ?? undefined
            }
        });

        if (response?.status >= 500) {
            alert('Lỗi tạo sản phẩm');
            return;
        }
        alert('Tạo sản phẩm thành công');
    };

    console.log('product: ', product);

    return (
        <form
            onSubmit={saveProduct}
            className="flex flex-col w-full max-w-[700px]"
        >
            <div className="flex flex-col gap-5">
                <h2 className="text-lg font-semibold">Thông tin cơ bản</h2>
                <FormInput
                    name="name"
                    label="Tên sản phẩm"
                    ref={nameRef}
                    bg="bg-white"
                />
                <AsyncSelectInput
                    label="product-types"
                    placeHolder="Loại sản phẩm"
                    selectedValue={product?.variety ?? 'Loại sản phẩm'}
                    asyncSelectorCallback={getProductTypeCallback}
                    onSelect={updateProductVariety}
                />

                <FileInput
                    fileSrc={fileSrc}
                    onChange={handleFileChange}
                    fallbackImage={
                        <GiFruitBowl
                            data-testid="default-icon"
                            className="w-full h-full min-[400px] text-gray-300"
                        />
                    }
                />
            </div>
            <DropdownContainer
                title={
                    product.categories.length > 0
                        ? `Hiện có ${product.categories.length} phân loại sản phẩm`
                        : 'Hiện chưa có phân loại sản phẩm nào'
                }
            >
                <ProductCategoryList
                    productCategories={product.categories}
                    onDelete={deleteProductCategory}
                />
            </DropdownContainer>
            <DropdownContainer title="Thêm phân loại sản phẩm">
                <ProductCategoryPicker addCategory={addCategory} />
            </DropdownContainer>
            <Button
                text="Lưu sản phẩm"
                type="submit"
                className="mt-5 text-emerald-500 border-emerald-500 hover:bg-emerald-50 mx-auto"
            />
        </form>
    );
};

export default CreateProductForm;
