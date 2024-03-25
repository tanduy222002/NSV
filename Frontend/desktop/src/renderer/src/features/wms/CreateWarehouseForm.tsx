import { useState, useRef } from 'react';
import { useModal } from '@renderer/hooks';
import { FormSection, FormTitle, FormInput, RackLayout } from './components';
import { FaBraille, FaMapLocationDot } from 'react-icons/fa6';
import { CgCloseR } from 'react-icons/cg';
import { MdWarehouse, MdLabelImportantOutline } from 'react-icons/md';
import { FaRegEdit } from 'react-icons/fa';

import { Button } from '@renderer/components';
import { Rack } from './type';

const CreateRackingLayoutForm = () => {
    const { closeModal } = useModal();
    const nameRef = useRef<HTMLInputElement>(null);
    const areaRef = useRef<HTMLInputElement>(null);
    const slotRef = useRef<HTMLInputElement>(null);
    const [rackLayout, setRackLayout] = useState<Rack[][]>([]);

    const addRackLine = () => {
        console.log(
            'name: ',
            nameRef?.current?.value,
            'slot: ',
            slotRef?.current?.value
        );

        const slot = parseInt(slotRef!.current!.value);
        const name = nameRef?.current?.value;

        const newRackLine = Array.from(Array(slot).keys())
            .map((slotId) => slotId + 1)
            .map((slotId) => ({
                name: `#${name}${slotId}`
            }));

        setRackLayout((prev) => [...prev, newRackLine]);
    };

    const handleCreateLayoutSubmit = () => {
        closeModal && closeModal();
    };

    return (
        <div className="inset-0 absolute bg-[#C2CDDB] bg-opacity-60 flex items-center justify-center">
            <form
                className="w-4/5 relative flex flex-col justify-center border border-[#1A3389] rounded-md bg-white py-5 px-8"
                onSubmit={handleCreateLayoutSubmit}
            >
                <CgCloseR
                    onClick={closeModal}
                    className="absolute top-5 right-5 text-[#1C274C] cursor-pointer w-[20px] h-[20px]"
                />
                <FormTitle title="Tạo kho mới" />
                <FormSection
                    title="Thông tin kho"
                    icon={<FaBraille />}
                    layoutClassName="flex flex-col gap-2"
                >
                    <div className="flex flex-col items-center gap-4">
                        <FormInput
                            name="Tên kho"
                            ref={slotRef}
                            icon={<MdLabelImportantOutline />}
                        />
                        <FormInput
                            name="Địa chỉ"
                            ref={areaRef}
                            icon={<FaMapLocationDot />}
                        />
                        <FormInput
                            name="Sơ đồ"
                            ref={nameRef}
                            icon={<MdWarehouse />}
                        />
                    </div>
                </FormSection>
                <FormSection
                    title="Xem trước"
                    icon={<MdWarehouse />}
                    layoutClassName="flex flex-col items-center relative"
                >
                    <FaRegEdit className="absolute -top-7 left-[110px] text-[#F78F1E] cursor-pointer" />
                    <RackLayout rackLayout={rackLayout} />
                    <FormInput
                        name="Tên sơ đồ"
                        ref={nameRef}
                        icon={<MdWarehouse />}
                    />
                    <Button
                        text="Lưu sơ đồ"
                        className="text-[#1A3389] border-[#1A3389] mx-auto mt-6"
                    />
                </FormSection>
                <Button
                    text="Lưu kho"
                    className="text-[#1A3389] border-[#1A3389] mx-auto mt-6"
                />
            </form>
        </div>
    );
};

export default CreateRackingLayoutForm;
