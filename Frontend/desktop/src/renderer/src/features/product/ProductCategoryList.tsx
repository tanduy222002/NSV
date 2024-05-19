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
        <>
            {productCategories.map((category, i) => (
                <div className="relative px-5 py-5" key={i}>
                    <CgCloseR
                        className="absolute w-[20px] h-[20px] hover:text-red-500 top-5 right-5"
                        onClick={() => onDelete(i)}
                    />
                    <p>Tên: {category.name}</p>
                    <img src={category.image} />
                    <h2 className="font-semibold text-sky-800 text-lg">
                        Giới hạn nhiệt độ:
                    </h2>
                    <div className="px-5">
                        <p className="text-sm">
                            <span className="font-semibold text-sky-800 text-base">
                                Cận dưới:
                            </span>{' '}
                            {category.lower_temperature_threshold}
                        </p>
                        <p className="text-sm">
                            <span className="font-semibold text-sky-800 text-base">
                                Cận trên:
                            </span>{' '}
                            {category.upper_temperature_threshold}
                        </p>
                    </div>
                    <p>Chất lượng:</p>
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
        </>
    );
};

export default ProductCategoryList;
