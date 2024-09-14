package com.mobileapp.f_m_a_petcare.HealthManage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.f_m_a_petcare.R;

import java.util.ArrayList;
import java.util.List;

public class HealthFragment extends Fragment implements SymptomAdapter.OnSymptomClickListener {

    private RecyclerView symptomsRecyclerView;
    private EditText searchEditText;
    private List<Symptom> symptoms;
    private SymptomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        symptomsRecyclerView = view.findViewById(R.id.symptomsRecyclerView);
        searchEditText = view.findViewById(R.id.searchEditText);

        symptoms = createSymptomsList();
        adapter = new SymptomAdapter(symptoms, this);
        symptomsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        symptomsRecyclerView.setAdapter(adapter);

        // Thêm TextWatcher để tìm kiếm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSymptoms(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });

        return view;
    }

    // Tạo danh sách các triệu chứng
    private List<Symptom> createSymptomsList() {
        List<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom(R.drawable.ic_health_pet1, "Ho, khó thở, thở gấp, khò khè",
                "Xem chi tiết",
                "Nguyên nhân: Các triệu chứng như ho, khó thở, thở gấp, và khò khè ở thú cưng thường là dấu hiệu của những vấn đề nghiêm trọng về hô hấp. Những triệu chứng này có thể xuất phát từ nhiều nguyên nhân khác nhau. Nhiễm trùng đường hô hấp do vi khuẩn hoặc virus có thể gây ra viêm đường hô hấp, dẫn đến ho và khó thở. Dị ứng với các chất như phấn hoa, bụi, hoặc hóa chất cũng có thể làm kích thích đường hô hấp, gây ra khò khè và thở nhanh.",
                "Chuẩn đoán: Triệu chứng về hô hấp. Khi thú cưng xuất hiện các triệu chứng như trên, việc chẩn đoán nguyên nhân là rất quan trọng để đưa ra phương án điều trị phù hợp. Bác sĩ thú y sẽ tiến hành khám lâm sàng, nghe tiếng tim và phổi để kiểm tra dấu hiệu bất thường. Xét nghiệm máu có thể được thực hiện để phát hiện nhiễm trùng hoặc dị ứng. Chụp X-quang phổi giúp bác sĩ nhìn rõ hơn về tình trạng phổi và hệ tim mạch của thú cưng. Trong trường hợp nghi ngờ có tắc nghẽn hoặc dị vật, nội soi đường hô hấp có thể được sử dụng để kiểm tra bên trong đường dẫn khí. Nếu có dấu hiệu bệnh tim, bác sĩ sẽ thực hiện siêu âm tim để đánh giá chức năng tim.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));  // Thêm link trang web

        symptoms.add(new Symptom(R.drawable.ic_health_pet2, "Chảy nước mũi, hắt hơi",
                "Xem chi tiết",
                "Nguyên nhân: Chảy nước mũi và hắt hơi ở thú cưng là những dấu hiệu phổ biến liên quan đến các vấn đề về đường hô hấp trên. Những triệu chứng này có thể do nhiều nguyên nhân khác nhau gây ra. Nhiễm trùng đường hô hấp do vi khuẩn hoặc virus có thể dẫn đến viêm mũi, làm thú cưng chảy nước mũi và hắt hơi liên tục. Dị ứng với các yếu tố môi trường như phấn hoa, nấm mốc, hoặc bụi bẩn cũng có thể gây kích ứng niêm mạc mũi, dẫn đến hắt hơi và chảy nước mũi.",
                "Chuẩn đoán: Triệu chứng về hô hấp. Khi thú cưng xuất hiện các triệu chứng chảy nước mũi và hắt hơi, bác sĩ thú y sẽ thực hiện các bước khám lâm sàng để tìm hiểu nguyên nhân. Việc kiểm tra kỹ lưỡng khoang mũi và tai là cần thiết để loại trừ dị vật hoặc khối u. Xét nghiệm máu có thể giúp xác định nhiễm trùng hoặc dị ứng. Nếu nghi ngờ viêm xoang hoặc có vấn đề trong cấu trúc mũi, bác sĩ có thể đề nghị chụp X-quang hoặc nội soi mũi để đánh giá rõ hơn tình trạng. Các xét nghiệm vi sinh để tìm vi khuẩn hoặc virus gây bệnh cũng có thể được tiến hành.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet3, "Hơi thở nặng, thở hổn hển",
                "Xem chi tiết",
                "Nguyên nhân: Hơi thở nặng và thở hổn hển ở thú cưng thường là dấu hiệu của các vấn đề về hệ hô hấp hoặc tuần hoàn. Những triệu chứng này có thể xuất phát từ nhiều nguyên nhân khác nhau. Một số nguyên nhân phổ biến bao gồm nhiễm trùng đường hô hấp, như viêm phổi hoặc viêm phế quản, khiến thú cưng khó thở và thở nặng nhọc. Dị ứng với các chất gây kích ứng trong không khí như phấn hoa, nấm mốc, hoặc khói cũng có thể dẫn đến hô hấp khó khăn và hơi thở hổn hển.",
                "Chuẩn đoán: Triệu chứng về hô hấp. Khi thú cưng có triệu chứng hơi thở nặng và thở hổn hển, bác sĩ thú y sẽ tiến hành các xét nghiệm để xác định nguyên nhân. Khám lâm sàng bao gồm nghe tim và phổi để kiểm tra bất kỳ tiếng bất thường nào. Xét nghiệm máu có thể giúp phát hiện nhiễm trùng hoặc các vấn đề về máu. Bác sĩ có thể sử dụng chụp X-quang hoặc siêu âm tim để kiểm tra tình trạng phổi và chức năng tim. Nếu nghi ngờ có vấn đề về đường hô hấp, nội soi phế quản có thể được chỉ định để kiểm tra chi tiết hơn về phổi và đường thở.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet4, "Nôn mửa, chán ăn, bỏ ăn",
                "Xem chi tiết",
                "Nguyên nhân: Nôn mửa, chán ăn, và bỏ ăn ở thú cưng là những dấu hiệu cảnh báo về các vấn đề sức khỏe nghiêm trọng. Những triệu chứng này có thể xuất phát từ nhiều nguyên nhân khác nhau. Nhiễm trùng đường tiêu hóa do vi khuẩn, virus, hoặc ký sinh trùng là một trong những nguyên nhân phổ biến, gây ra tình trạng buồn nôn, nôn mửa và mất cảm giác thèm ăn. Các vấn đề về tiêu hóa như viêm dạ dày, viêm tụy, hoặc loét dạ dày cũng có thể khiến thú cưng nôn mửa và không muốn ăn.",
                "Chuẩn đoán: Triệu chứng về tiêu hóa. Khi thú cưng xuất hiện triệu chứng nôn mửa và bỏ ăn, việc xác định nguyên nhân cần dựa trên nhiều phương pháp chẩn đoán. Bác sĩ thú y sẽ tiến hành khám lâm sàng và hỏi chi tiết về lịch sử ăn uống cũng như các triệu chứng kèm theo. Xét nghiệm máu có thể được thực hiện để kiểm tra chức năng gan, thận, hoặc phát hiện dấu hiệu nhiễm trùng. Siêu âm hoặc chụp X-quang vùng bụng có thể giúp bác sĩ phát hiện các bất thường trong hệ tiêu hóa như dị vật, viêm, hoặc khối u. Nếu nghi ngờ các bệnh lý về gan hoặc tụy, xét nghiệm chuyên sâu như xét nghiệm chức năng gan hoặc sinh thiết có thể được yêu cầu.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet5, "Táo bón, bụng phình to, đau bụng",
                "Xem chi tiết",
                "Nguyên nhân: Táo bón, bụng phình to, và đau bụng ở thú cưng thường là dấu hiệu của các vấn đề tiêu hóa hoặc bệnh lý nghiêm trọng khác. Táo bón có thể xảy ra khi phân trở nên khô và cứng, khiến thú cưng khó đi vệ sinh. Nguyên nhân phổ biến của táo bón bao gồm thiếu nước, chế độ ăn uống thiếu chất xơ, hoặc lười vận động. Thú cưng cũng có thể bị táo bón nếu nuốt phải lông, dị vật hoặc xương, gây tắc nghẽn đường tiêu hóa.",
                "Chuẩn đoán: Triệu chứng về tiêu hóa. Khi thú cưng có triệu chứng táo bón, bụng phình to và đau bụng, bác sĩ thú y sẽ thực hiện một loạt các xét nghiệm để xác định nguyên nhân. Khám lâm sàng bao gồm việc kiểm tra bụng để đánh giá tình trạng phình to hoặc đau. Bác sĩ có thể yêu cầu chụp X-quang hoặc siêu âm bụng để xác định tắc nghẽn, sự hiện diện của khí, chất lỏng, hoặc khối u. Xét nghiệm máu cũng có thể được thực hiện để kiểm tra chức năng gan, thận, hoặc phát hiện dấu hiệu viêm nhiễm. Nếu nghi ngờ có tình trạng viêm ruột hoặc các vấn đề về tiêu hóa, bác sĩ có thể đề nghị nội soi để kiểm tra chi tiết bên trong đường ruột.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet6, "Ngứa, gãi nhiều, rụng lông",
                "Xem chi tiết",
                "Nguyên nhân: Ngứa, gãi nhiều, và rụng lông ở thú cưng là dấu hiệu thường gặp khi chúng gặp các vấn đề về da hoặc dị ứng. Nguyên nhân gây ngứa và gãi nhiều có thể bắt nguồn từ ký sinh trùng như bọ chét, ve, hoặc chấy rận. Những ký sinh trùng này cắn và kích thích da, gây ngứa và khiến thú cưng gãi liên tục. Dị ứng với thực phẩm, phấn hoa, bụi, hoặc hóa chất như xà phòng và thuốc tẩy cũng có thể khiến da bị kích ứng và gây ra ngứa.",
                "Chuẩn đoán: Triệu chứng về da. Khi thú cưng có triệu chứng ngứa, gãi nhiều và rụng lông, bác sĩ thú y sẽ tiến hành khám lâm sàng da và lông để đánh giá tình trạng. Bác sĩ có thể sử dụng kính hiển vi để kiểm tra sự hiện diện của ký sinh trùng trên da. Nếu nghi ngờ nhiễm nấm hoặc vi khuẩn, bác sĩ có thể lấy mẫu da hoặc lông để thực hiện các xét nghiệm vi sinh nhằm xác định nguyên nhân gây nhiễm trùng. Xét nghiệm máu có thể giúp phát hiện dị ứng hoặc các bệnh lý về miễn dịch. Nếu thú cưng có dấu hiệu dị ứng thực phẩm, bác sĩ có thể yêu cầu thay đổi chế độ ăn uống để xác định nguyên nhân gây dị ứng.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet7, "Co giật, mất cân bằng",
                "Xem chi tiết",
                "Nguyên nhân: Co giật và mất cân bằng ở thú cưng là những triệu chứng nghiêm trọng liên quan đến hệ thần kinh hoặc các vấn đề sức khỏe khác. Co giật có thể xuất hiện dưới dạng rung giật cơ, cử động bất thường, hoặc mất ý thức tạm thời. Nguyên nhân phổ biến của co giật bao gồm động kinh, là một bệnh mãn tính ảnh hưởng đến hệ thần kinh trung ương. Co giật cũng có thể xuất phát từ ngộ độc do tiếp xúc với hóa chất độc hại, thuốc, hoặc thức ăn không an toàn. Các bệnh lý về não, như viêm màng não hoặc u não, cũng là những nguyên nhân nghiêm trọng gây ra co giật.",
                "Chuẩn đoán: Triệu chứng về thần kinh. Khi thú cưng có triệu chứng co giật và mất cân bằng, bác sĩ thú y sẽ tiến hành kiểm tra lâm sàng chi tiết, bao gồm đánh giá hành vi thần kinh và khả năng vận động của thú cưng. Xét nghiệm máu có thể được thực hiện để kiểm tra tình trạng ngộ độc, mất cân bằng điện giải, hoặc bệnh lý gan thận. Chụp MRI hoặc CT não có thể giúp bác sĩ phát hiện các bất thường trong não như khối u, viêm, hoặc tổn thương.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet8, "Tiểu khó, tiểu ra máu",
                "Xem chi tiết",
                "Nguyên nhân:  Tiểu khó và tiểu ra máu ở thú cưng là dấu hiệu của các vấn đề về hệ tiết niệu hoặc sinh dục, có thể xuất phát từ nhiều nguyên nhân khác nhau. Tiểu khó, bao gồm việc rặn nhiều khi đi tiểu hoặc tiểu không hết, thường là dấu hiệu của tình trạng tắc nghẽn đường tiết niệu. Nguyên nhân phổ biến có thể bao gồm sỏi bàng quang, sỏi thận, hoặc viêm đường tiết niệu. Sỏi thận hoặc sỏi bàng quang hình thành khi khoáng chất trong nước tiểu kết tủa thành các tinh thể cứng, làm cản trở dòng nước tiểu, gây ra tiểu khó và đau đớn.",
                "Chuẩn đoán: Triệu chứng về tiết niệu. Để xác định nguyên nhân của triệu chứng tiểu khó và tiểu ra máu, bác sĩ thú y sẽ thực hiện các xét nghiệm lâm sàng và xét nghiệm nước tiểu để kiểm tra sự hiện diện của máu, vi khuẩn, và tinh thể sỏi. Xét nghiệm máu có thể giúp kiểm tra chức năng thận và phát hiện dấu hiệu nhiễm trùng hoặc viêm. Chụp X-quang hoặc siêu âm vùng bụng có thể được sử dụng để xác định sự hiện diện của sỏi, khối u, hoặc tắc nghẽn trong hệ tiết niệu.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));
        symptoms.add(new Symptom(R.drawable.ic_health_pet9, "Mắt đỏ, viêm kết mạc",
                "Xem chi tiết",
                "Nguyên nhân: Mắt đỏ và viêm kết mạc ở thú cưng là những triệu chứng liên quan đến các vấn đề về mắt, đặc biệt là viêm nhiễm ở màng kết mạc (một lớp mỏng bao phủ phía trong mi mắt và bề mặt trước của mắt). Viêm kết mạc có thể do nhiều nguyên nhân khác nhau, bao gồm nhiễm trùng vi khuẩn, virus, hoặc nấm. Nhiễm trùng thường khiến mắt đỏ, chảy nhiều nước mắt, và gây cảm giác ngứa rát, làm thú cưng dụi mắt liên tục.",
                "Chuẩn đoán: Triệu chứng về mắt. Khi thú cưng có triệu chứng mắt đỏ và viêm kết mạc, bác sĩ thú y sẽ tiến hành khám mắt để đánh giá mức độ viêm và tìm hiểu nguyên nhân. Bác sĩ có thể kiểm tra kết mạc và giác mạc bằng đèn chiếu đặc biệt để tìm tổn thương hoặc sự hiện diện của dị vật trong mắt. Xét nghiệm mắt bằng cách nhỏ thuốc nhuộm đặc biệt có thể giúp phát hiện loét giác mạc. Nếu nghi ngờ có nhiễm trùng, bác sĩ có thể lấy mẫu dịch từ mắt để tiến hành xét nghiệm vi sinh nhằm xác định loại vi khuẩn, virus, hoặc nấm gây bệnh.",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra...",
                "https://vcahospitals.com/"));

        // Thêm các triệu chứng khác tương tự với liên kết trang web
        return symptoms;
    }




    private void filterSymptoms(String keyword) {
        String searchKeyword = keyword.toLowerCase();  // Chuyển từ khóa sang viết thường

        List<Symptom> filteredSymptoms = new ArrayList<>();

        for (Symptom symptom : symptoms) {
            String title = symptom.getTitle().toLowerCase();  // Chuyển tiêu đề sang viết thường
            if (title.contains(searchKeyword)) {
                filteredSymptoms.add(symptom);
            }
        }

        adapter.updateSymptomsList(filteredSymptoms);  // Cập nhật danh sách hiển thị
    }


    @Override
    public void onSymptomClick(Symptom symptom) {
        SymptomDetailDialogFragment detailFragment = SymptomDetailDialogFragment.newInstance(symptom);
        detailFragment.show(getChildFragmentManager(), "symptom_detail");
    }
}
