package ru.abelov.schemetestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.abelov.schemecomponent.ControlTableList;
import ru.abelov.schemecomponent.OnTableSelectListener;
import ru.abelov.schemecomponent.TableStatusData;
import ru.abelov.schemecomponent.entity.SectionEntity;
import ru.abelov.schemecomponent.entity.Store;
import ru.abelov.schemecomponent.entity.TableEntity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cTableList)
    ControlTableList cPlace;

    TableStatusData data;

    private OnTableSelectListener listener = new OnTableSelectListener() {
        @Override
        public void onTableSelect(TableEntity table) {

        }
    };

    private SectionEntity section;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
//        section = new Gson().fromJson("{\"id\":77,\"brandId\":4,\"storeId\":8,\"name\":\"Малый зал\",\"schemaImg\":\"/image-resource/schema/it_st_1508833700856_Схема_Fanny_Kabany_студия_1.jpg\",\"adminId\":\"\",\"ordinal\":0,\"tables\":[{\"id\":1028,\"brandId\":4,\"storeId\":8,\"sectionId\":77,\"ordinal\":0,\"name\":\"Столик у окна\",\"enabled\":true,\"orderEnabled\":true,\"posNo\":\"\",\"orderId\":null,\"masterTableNo\":null,\"adminId\":\"\",\"capacity\":6,\"mapX\":261,\"mapY\":37,\"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\"tableStatuses\":[{\"id\":439,\"tableId\":1028,\"userId\":428,\"orderBegin\":1522243215000,\"orderEnd\":1522243515000}],\"deposit\":1.0,\"used\":false},{\"id\":1029,\"brandId\":4,\"storeId\":8,\"sectionId\":77,\"ordinal\":0,\"name\":\"Столик у сцены\",\"enabled\":true,\"orderEnabled\":true,\"posNo\":\"\",\"orderId\":null,\"masterTableNo\":null,\"adminId\":\"\",\"capacity\":6,\"mapX\":727,\"mapY\":217,\"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\"tableStatuses\":null,\"deposit\":4300.0,\"used\":false},{\"id\":1296,\"brandId\":4,\"storeId\":8,\"sectionId\":77,\"ordinal\":0,\"name\":\"Златый\",\"enabled\":true,\"orderEnabled\":true,\"posNo\":\"\",\"orderId\":null,\"masterTableNo\":null,\"adminId\":\"\",\"capacity\":4,\"mapX\":627,\"mapY\":57,\"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\"tableStatuses\":null,\"deposit\":1.0,\"used\":false},{\"id\":1363,\"brandId\":4,\"storeId\":8,\"sectionId\":77,\"ordinal\":0,\"name\":\"\",\"enabled\":true,\"orderEnabled\":true,\"posNo\":\"\",\"orderId\":null,\"masterTableNo\":null,\"adminId\":\"\",\"capacity\":4,\"mapX\":447,\"mapY\":91,\"imagePath\":\"\n" +
//                "03-21 11:59:38.478 5832-5858/com.bak.app D/OkHttp: http://img.bakapp.ru/image/table/instance/file?instance=11\",\"tableStatuses\":null,\"deposit\":0.0,\"used\":false},{\"id\":1364,\"brandId\":4,\"storeId\":8,\"sectionId\":77,\"ordinal\":0,\"name\":\"\",\"enabled\":true,\"orderEnabled\":true,\"posNo\":\"\",\"orderId\":null,\"masterTableNo\":null,\"adminId\":\"\",\"capacity\":4,\"mapX\":567,\"mapY\":171,\"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\"tableStatuses\":null,\"deposit\":0.0,\"used\":false}]},{\"id\":79,\"brandId\":4,\"storeId\":8,\"name\":\"Доставка\",\"schemaImg\":null,\"adminId\":\"\",\"ordinal\":0,\"tables\":null}", SectionEntity.class);

        section = new Gson().fromJson("{\n" +
                "   \"id\":77,\n" +
                "   \"brandId\":4,\n" +
                "   \"storeId\":8,\n" +
                "   \"name\":\"Малый зал\",\n" +
                "   \"schemaImg\":\"/image-resource/schema/it_st_1508833700856_Схема_Fanny_Kabany_студия_1.jpg\",\n" +
                "   \"adminId\":\"\",\n" +
                "   \"ordinal\":0,\n" +
                "   \"tables\":[\n" +
                "      {\n" +
                "         \"id\":1028,\n" +
                "         \"brandId\":4,\n" +
                "         \"storeId\":8,\n" +
                "         \"sectionId\":77,\n" +
                "         \"ordinal\":0,\n" +
                "         \"name\":\"Столик у окна\",\n" +
                "         \"enabled\":true,\n" +
                "         \"orderEnabled\":true,\n" +
                "         \"posNo\":\"\",\n" +
                "         \"orderId\":null,\n" +
                "         \"masterTableNo\":null,\n" +
                "         \"adminId\":\"\",\n" +
                "         \"capacity\":6,\n" +
                "         \"mapX\":261,\n" +
                "         \"mapY\":37,\n" +
                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
                "         \"tableStatuses\":[\n" +
                "            {\n" +
                "               \"id\":439,\n" +
                "               \"tableId\":1028,\n" +
                "               \"userId\":428,\n" +
                "               \"orderBegin\":1522243215000,\n" +
                "               \"orderEnd\":1522243515000\n" +
                "            }\n" +
                "         ],\n" +
                "         \"deposit\":1.0,\n" +
                "         \"used\":false\n" +
                "      },\n" +
                "      {\n" +
                "         \"id\":1029,\n" +
                "         \"brandId\":4,\n" +
                "         \"storeId\":8,\n" +
                "         \"sectionId\":77,\n" +
                "         \"ordinal\":0,\n" +
                "         \"name\":\"Столик у сцены\",\n" +
                "         \"enabled\":true,\n" +
                "         \"orderEnabled\":true,\n" +
                "         \"posNo\":\"\",\n" +
                "         \"orderId\":null,\n" +
                "         \"masterTableNo\":null,\n" +
                "         \"adminId\":\"\",\n" +
                "         \"capacity\":6,\n" +
                "         \"mapX\":727,\n" +
                "         \"mapY\":217,\n" +
                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
                "         \"tableStatuses\":null,\n" +
                "         \"deposit\":4300.0,\n" +
                "         \"used\":false\n" +
                "      },\n" +
                "      {\n" +
                "         \"id\":1296,\n" +
                "         \"brandId\":4,\n" +
                "         \"storeId\":8,\n" +
                "         \"sectionId\":77,\n" +
                "         \"ordinal\":0,\n" +
                "         \"name\":\"Златый\",\n" +
                "         \"enabled\":true,\n" +
                "         \"orderEnabled\":true,\n" +
                "         \"posNo\":\"\",\n" +
                "         \"orderId\":null,\n" +
                "         \"masterTableNo\":null,\n" +
                "         \"adminId\":\"\",\n" +
                "         \"capacity\":4,\n" +
                "         \"mapX\":627,\n" +
                "         \"mapY\":57,\n" +
                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
                "         \"tableStatuses\":null,\n" +
                "         \"deposit\":1.0,\n" +
                "         \"used\":false\n" +
                "      },\n" +
                "      {\n" +
                "         \"id\":1363,\n" +
                "         \"brandId\":4,\n" +
                "         \"storeId\":8,\n" +
                "         \"sectionId\":77,\n" +
                "         \"ordinal\":0,\n" +
                "         \"name\":\"\",\n" +
                "         \"enabled\":true,\n" +
                "         \"orderEnabled\":true,\n" +
                "         \"posNo\":\"\",\n" +
                "         \"orderId\":null,\n" +
                "         \"masterTableNo\":null,\n" +
                "         \"adminId\":\"\",\n" +
                "         \"capacity\":4,\n" +
                "         \"mapX\":447,\n" +
                "         \"mapY\":91,\n" +
                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
                "         \"tableStatuses\":null,\n" +
                "         \"deposit\":0.0,\n" +
                "         \"used\":false\n" +
                "      },\n" +
                "      {\n" +
                "         \"id\":1364,\n" +
                "         \"brandId\":4,\n" +
                "         \"storeId\":8,\n" +
                "         \"sectionId\":77,\n" +
                "         \"ordinal\":0,\n" +
                "         \"name\":\"\",\n" +
                "         \"enabled\":true,\n" +
                "         \"orderEnabled\":true,\n" +
                "         \"posNo\":\"\",\n" +
                "         \"orderId\":null,\n" +
                "         \"masterTableNo\":null,\n" +
                "         \"adminId\":\"\",\n" +
                "         \"capacity\":4,\n" +
                "         \"mapX\":567,\n" +
                "         \"mapY\":171,\n" +
                "         \"imagePath\":\"http://img.bakapp.ru/image/table/instance/file?instance=11\",\n" +
                "         \"tableStatuses\":null,\n" +
                "         \"deposit\":0.0,\n" +
                "         \"used\":false\n" +
                "      }\n" +
                "   ]\n" +
                "}", SectionEntity.class);

        Store store = new Store();
        store.orderBegin = "0700";
        store.orderEnd = "2300";

        data = new TableStatusData(this, 1521622772773L, store, 1521622772773L, 1800000L, 3600000L, 2);


        cPlace.setListener(listener);

        cPlace.setData(data);
        cPlace.setSection(section);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}
