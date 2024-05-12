import { useQuery } from '@tanstack/react-query';
import { useNavigate, useParams } from 'react-router-dom';
import { IoChevronBack } from 'react-icons/io5';
import { UserInfo } from '@renderer/components';
import { useLocalStorage } from '@renderer/hooks';
import { getPartnerDetail } from '@renderer/services/api';
import { PartnerDetailMainSection } from '@renderer/features/partner';
import partnerIconSrc from '@renderer/assets/partner-icon.png';

const PartnerDetailPage = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const goToPartnerPage = () => navigate('/partner');

    const { getItem } = useLocalStorage('access-token');
    const accessToken = getItem();

    const { data, isFetching } = useQuery({
        queryKey: ['partner', id],
        queryFn: () =>
            getPartnerDetail({
                token: accessToken,
                partnerId: id as string
            })
    });

    if (!isFetching) console.log('data: ', data);

    return (
        <div className="w-full px-5 py-5">
            <UserInfo />
            <div className="flex items-center gap-2">
                <IoChevronBack
                    className="text-blue-800 h-[30px] w-[30px] px-1 py-1 cursor-pointer hover:bg-blue-50 rounded-full"
                    onClick={goToPartnerPage}
                />
                <img alt="form-icon" src={partnerIconSrc} />
                <h1 className="text-xl font-semibold">Đối tác</h1>
            </div>
            {isFetching ? (
                <h1>Loading...</h1>
            ) : (
                <PartnerDetailMainSection
                    name={data?.name}
                    phoneNumber={data?.phone}
                    address={data?.address_string}
                    bankAccount={data?.bankAccount}
                    taxNumber={data?.taxNumber}
                    totalTransactionCount={data?.total_transaction}
                    totalTransactionValue={data?.total_transaction_amount}
                />
            )}
        </div>
    );
};

export default PartnerDetailPage;
