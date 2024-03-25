import FilterSection from './FilterSection';
import { FaSearch } from 'react-icons/fa';

const ExportFomFilterStep = () => {
    return (
        <div className="grid grid-cols-3 gap-4">
            <div className="flex flex-col gap-4">
                <FilterSection
                    title="Kho hàng"
                    options={['Kho 1', 'Kho 2', 'Kho 3', 'Kho 4']}
                />
                <FilterSection
                    title="Sản phẩm"
                    options={[
                        'Sầu riêng',
                        'Măng cụt',
                        'Cam sành',
                        'Dâu tây',
                        'Thanh long',
                        'Nhãn'
                    ]}
                />
            </div>

            <div className="flex flex-col gap-4">
                <FilterSection
                    title="Chủng loại"
                    options={['Ri 6', 'Thái', 'Musan King', 'Chuồng bò']}
                />
                <FilterSection
                    title="Loại hàng"
                    options={['Loại 1', 'Loại 2', 'Loại 3', 'Loại 4']}
                />
            </div>

            <div className="h-full flex flex-col">
                <div className="border border-[#E8E8E8] rounded-md py-6 px-6">
                    <h1 className="text-lg font-semibold mb-2">
                        Lọc theo khối lượng
                    </h1>
                    <div className="flex items-center gap-2">
                        <div className="px-3 py-2 bg-[#F9F9F9] text-[#E6E6E6] border border-[#E6E6E6] rounded-md">
                            <input className="w-full" />
                        </div>
                        <div className="px-3 py-2 bg-[#F9F9F9] text-[#E6E6E6] border border-[#E6E6E6] rounded-md">
                            <input className="w-full" />
                        </div>
                    </div>
                </div>
                <button className="px-3 py-2 rounded-md bg-[#1A3389] w-full flex justify-center my-auto">
                    <FaSearch className="text-white" />
                </button>
            </div>
        </div>
    );
};

export default ExportFomFilterStep;
