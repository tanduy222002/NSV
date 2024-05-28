import { CgCloseR } from 'react-icons/cg';
import { ProductCategory } from '@renderer/types/product';

type ProductCategoryListProps = {
    productCategories: ProductCategory[];
    onDelete: (index: number) => void;
};

const ProductCategoryList = ({
    productCategories,
    onDelete
}: ProductCategoryListProps) => {
    return (
        <div className="flex gap-5 items-center my-3">
            {productCategories.map((category, i) => (
                <div
                    className="relative p-5 border border-gray-300 rounded-md"
                    key={i}
                >
                    <CgCloseR
                        className="absolute w-[20px] h-[20px] hover:text-red-500 top-5 right-5"
                        onClick={() => onDelete(i)}
                    />
                    <p className="font-semibold text-sky-800">
                        Tên: {category.name}
                    </p>
                    <div className="w-[200px] h-[200px] border border-gray-300 rounded-md overflow-hidden my-2">
                        <img src={category.image} className="object-content" />
                    </div>
                    <h2 className="font-semibold text-sky-800 text-lg">
                        Giới hạn nhiệt độ:
                    </h2>
                    <div className="px-5">
                        <p className="text-sm text-sky-800 font-semibold">
                            <span className="font-semibold text-sky-800 text-base">
                                Cận dưới:
                            </span>{' '}
                            {category.lower_temperature_threshold} °C
                        </p>
                        <p className="text-sm text-sky-800 font-semibold">
                            <span className="font-semibold text-sky-800 text-base">
                                Cận trên:
                            </span>{' '}
                            {category.upper_temperature_threshold} °C
                        </p>
                    </div>
                    <p className="font-semibold text-sky-800 my-2">
                        Chất lượng:
                    </p>
                    <div className="flex items-center gap-3">
                        {category.qualities.map((quality, i) => (
                            <div
                                key={i}
                                className="group relative flex items-center gap-2 text-sm font-semibold px-2 py-1 w-fit rounded-md text-sky-800 border border-sky-800"
                            >
                                <p>{quality.name}</p>
                                <p className="absolute -bottom-5 left-3 w-fit bg-sky-800 text-xs px-1 rounded-sm text-white invisible group-hover:visible">
                                    {quality.description}
                                </p>
                            </div>
                        ))}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ProductCategoryList;
