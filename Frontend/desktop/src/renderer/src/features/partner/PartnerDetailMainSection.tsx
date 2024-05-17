import { FaUserCog } from 'react-icons/fa';
import { RiMoneyDollarCircleLine } from 'react-icons/ri';
import { FaPhone } from 'react-icons/fa';
import { IoLocationOutline } from 'react-icons/io5';
import { FaMoneyCheckAlt } from 'react-icons/fa';
import { MdOutlineEmail } from 'react-icons/md';
import { TbPigMoney } from 'react-icons/tb';
import { BiTransfer } from 'react-icons/bi';
import { DataField } from '@renderer/components';
import { formatNumber } from '@renderer/utils/formatText';

type PartnerDetailMainSectionProps = {
    name: string;
    phoneNumber: string;
    address: string;
    email: string;
    bankAccount: string;
    taxNumber: string;
    totalTransactionCount: number;
    totalTransactionValue: number;
};

const PartnerDetailMainSection = ({
    name,
    phoneNumber,
    address,
    email,
    bankAccount,
    taxNumber,
    totalTransactionCount,
    totalTransactionValue
}: PartnerDetailMainSectionProps) => {
    return (
        <div className="flex gap-5 max-w-[900px] mt-5">
            <div className="space-y-5 flex-1">
                <DataField
                    name="Tên đối tác"
                    icon={<FaUserCog />}
                    disabled={false}
                    value={name}
                    defaultValue="Tên đối tác"
                />
                <DataField
                    name={'Số điện thoại'}
                    icon={<FaPhone />}
                    disabled={false}
                    value={phoneNumber}
                    defaultValue={'Số điện thoại'}
                />
                <DataField
                    name={'Email'}
                    icon={<MdOutlineEmail />}
                    disabled={false}
                    value={email}
                    defaultValue={'Email'}
                />
                <DataField
                    name={'Địa chỉ'}
                    icon={<IoLocationOutline />}
                    disabled={false}
                    value={address}
                    defaultValue={'Địa chỉ'}
                />
            </div>
            <div className="space-y-5 flex-1">
                <DataField
                    name={'Tài khoản NH'}
                    icon={<FaMoneyCheckAlt />}
                    disabled={false}
                    value={bankAccount}
                    defaultValue={'Tài khoản NH'}
                />
                <DataField
                    name={'Mã số thuế'}
                    icon={<TbPigMoney />}
                    disabled={false}
                    value={taxNumber}
                    defaultValue={'Mã số thuế'}
                />
                <DataField
                    name="Số giao dịch"
                    icon={<BiTransfer className="rotate-90" />}
                    disabled={false}
                    value={totalTransactionCount}
                    defaultValue={'Trạng thái'}
                />
                <DataField
                    name={'Tổng giá trị'}
                    icon={<RiMoneyDollarCircleLine />}
                    disabled={false}
                    value={`${formatNumber(totalTransactionValue)} VND`}
                    defaultValue={'Tổng giá trị'}
                    textColor="text-sky-800"
                />
            </div>
        </div>
    );
};

export default PartnerDetailMainSection;
