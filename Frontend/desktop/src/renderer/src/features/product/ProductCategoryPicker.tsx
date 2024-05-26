import { useRef, useState } from 'react';
import { IoCloseSharp } from 'react-icons/io5';
import { MdOutlineAddBox } from 'react-icons/md';
import { FormInput, Button, FileInput } from '@renderer/components';
import { Quality, ProductCategory } from '@renderer/types/product';
import { GiFruitBowl } from 'react-icons/gi';

type ProductCategoryPickerProps = {
    addCategory: (categories: ProductCategory) => void;
};

const ProductCategoryPicker = ({ addCategory }: ProductCategoryPickerProps) => {
    const categoryNameRef = useRef<HTMLInputElement>(null);
    const uppperTempRef = useRef<HTMLInputElement>(null);
    const lowerTempRef = useRef<HTMLInputElement>(null);
    const qualityNameRef = useRef<HTMLInputElement>(null);
    const qualityDescriptionRef = useRef<HTMLInputElement>(null);

    const [categoryImage, setCategoryImage] = useState<string | undefined>(
        undefined
    );
    const updateImage = (src: string) => {
        setCategoryImage(src);
    };

    const [qualities, setQualities] = useState<Quality[]>([]);
    const addQuality = () => {
        const newQuality: Quality = {
            name: qualityNameRef?.current?.value ?? '',
            description: qualityDescriptionRef?.current?.value ?? ''
        };
        setQualities((prev) => [newQuality, ...prev]);
    };
    const removeQuality = (id: number) => {
        setQualities((prev) => prev.filter((_, qualityId) => qualityId !== id));
    };

    const saveCategory = () => {
        if (
            !Number(lowerTempRef?.current?.value) ||
            !Number(uppperTempRef?.current?.value)
        ) {
            alert('Nhiệt độ không hợp lệ');
            return;
        }
        addCategory({
            name: categoryNameRef?.current?.value ?? '',
            seasonal: 'string',
            image: categoryImage,
            lower_temperature_threshold: lowerTempRef?.current?.value ?? '0',
            upper_temperature_threshold: uppperTempRef?.current?.value ?? '0',
            qualities: [...qualities]
        });
    };

    return (
        <div className="w-full">
            <h2 className="mb-3 font-semibold text-base">Thông tin chung</h2>
            <FormInput
                name="name"
                label="Loại sản phẩm"
                bg="bg-white"
                ref={categoryNameRef}
            />
            <FileInput
                fileSrc={categoryImage}
                onChange={updateImage}
                fallbackImage={
                    <GiFruitBowl
                        data-testid="default-icon"
                        className="w-full h-full min-[400px] text-gray-300"
                    />
                }
            />
            <h2 className="mb-3 font-semibold text-base">Nhiệt độ bảo quản</h2>
            <div className="flex items-center gap-3">
                <FormInput
                    name="name"
                    label="Cận dưới"
                    bg="bg-white"
                    ref={lowerTempRef}
                />
                <FormInput
                    name="name"
                    label="Cận trên"
                    bg="bg-white"
                    ref={uppperTempRef}
                />
            </div>
            <div className="flex flex-col gap-3 mb-5">
                <h2 className="font-semibold text-base">Chất lượng</h2>
                <FormInput
                    name="name"
                    label="Tên"
                    bg="bg-white"
                    ref={qualityNameRef}
                />
                <FormInput
                    name="name"
                    label="Mô tả"
                    bg="bg-white"
                    ref={qualityDescriptionRef}
                />
                <Button
                    text="Thêm"
                    className="text-emerald-500 border-emerald-500 hover:bg-emerald-50 mx-auto"
                    icon={<MdOutlineAddBox />}
                    action={addQuality}
                />
                <div className="flex items-center gap-3">
                    {qualities.map((quality, i) => (
                        <div
                            key={i}
                            className="group relative flex items-center gap-2 text-sm font-semibold px-2 py-1 w-fit rounded-md text-sky-800 border border-sky-800"
                        >
                            <p>{quality.name}</p>
                            <p className="absolute -bottom-5 left-3 w-fit bg-sky-800 text-xs px-1 rounded-sm text-white invisible group-hover:visible">
                                {quality.description}
                            </p>
                            <IoCloseSharp
                                className="hover:text-red-500"
                                onClick={() => removeQuality(i)}
                            />
                        </div>
                    ))}
                </div>
            </div>
            <Button
                text="Thêm loại sản phẩm"
                className="text-emerald-500 border-emerald-500 hover:bg-emerald-50 w-fit mx-auto"
                icon={<MdOutlineAddBox />}
                action={saveCategory}
            />
        </div>
    );
};

export default ProductCategoryPicker;
