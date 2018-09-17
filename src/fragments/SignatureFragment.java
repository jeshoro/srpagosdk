package sr.pago.sdk.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import io.realm.Realm;
import io.realm.RealmResults;
import sr.pago.sdk.R;
import sr.pago.sdk.SrPagoTransaction;
import sr.pago.sdk.api.ServiceCore;
import sr.pago.sdk.connection.ServiceCoreTransactionV2;
import sr.pago.sdk.definitions.Definitions;
import sr.pago.sdk.enums.Pixzelle;
import sr.pago.sdk.model.SPResponse;
import sr.pago.sdk.object.Global;
import sr.pago.sdk.object.response.TicketsResponse;
import sr.pago.sdk.utils.Logger;

/**
 * Created by Rodolfo on 24/03/2015.
 */
public class SignatureFragment extends BaseFragment {

    private Activity context;
    private TextView lblAccept;
    private SignaturePad pad;
    private TextView lblDelete;


    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    String cardHolderName;
    String amount;
    String token;
    String numTransaction;

    TextView lblTitle;
    TextView lblAmount;
    TextView lblTip;

    int lengt0 = 0;
    int length1 = 0;

    boolean dialogShowing = false;

    int layout;

    /**
     * This method create a new instance of the class SignatureFragment
     *
     * @param operation
     * @return
     */
    public static SignatureFragment newInstance(String operation, int layout) {
        SignatureFragment signatureFragment = new SignatureFragment();
        Bundle args = new Bundle();
        args.putString(Definitions.OPERATION_SIGNATURE, operation);
        signatureFragment.setArguments(args);
        signatureFragment.layout = layout;
        return signatureFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            JSONObject jsonObject;
            String operation = getArguments().getString(Definitions.OPERATION_SIGNATURE);
            try {
                jsonObject = new JSONObject(operation);
                cardHolderName = jsonObject.getJSONObject("result").getJSONObject("recipe").getJSONObject("card").getString("holder_name");
                amount = jsonObject.getJSONObject("result").getJSONObject("recipe").getJSONObject("total").getString("amount");
                token = jsonObject.getJSONObject("result").getString("token");
                numTransaction = jsonObject.getJSONObject("result").getJSONObject("recipe").getString("transaction");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAmountString() {
        return "";
    }

    public String getTipString() {
        return "";
    }

    public int getLayout() {
        return R.layout.fragment_payment_signature;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(getLayout(), container, false);
        setRetainInstance(true);
        final TextView lblCardHolderName = (TextView) rootView.findViewById(R.id.lbl_card_holder_name);
        final TextView lblpolicy = (TextView) rootView.findViewById(R.id.lbl_policy);
        lblCardHolderName.setTypeface(Typeface.createFromAsset(context.getAssets(), Definitions.GOTHAM()));
        lblpolicy.setTypeface(Typeface.createFromAsset(context.getAssets(), Definitions.GOTHAM()));
        lblCardHolderName.setText(cardHolderName);
        if (("").equals(lblCardHolderName)) {
            lblCardHolderName.setVisibility(View.GONE);
        } else {
            lblCardHolderName.setVisibility(View.VISIBLE);
        }
        pad = (SignaturePad) rootView.findViewById(R.id.canvas);
        pad.setPenColor(Color.BLACK);
        lblTitle = (TextView) rootView.findViewById(R.id.lbl_title);
        lblAccept = (TextView) rootView.findViewById(R.id.lbl_activate);
        lblDelete = (TextView) rootView.findViewById(R.id.lbl_clear);
        lblAmount = (TextView) rootView.findViewById(R.id.lbl_amount);
        lblTip = (TextView) rootView.findViewById(R.id.lbl_tip);
        lblTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), Definitions.GOTHAM()));
        lblAccept.setTypeface(Typeface.createFromAsset(context.getAssets(), Definitions.GOTHAM()));
        lblDelete.setTypeface(Typeface.createFromAsset(context.getAssets(), Definitions.GOTHAM()));
        lblAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSignature();
            }
        });
        lblDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pad.clear();
            }
        });

        lblAmount.setText(getAmountString());
        lblTip.setText(getTipString());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        try {
            //lblTitle.setText(String.format(context.getResources().getString(R.string.sr_pago_payment_signature_title), amount));
            if (dialogShowing) {
                alertDialog.show();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (dialogShowing) {
            alertDialog.dismiss();
        }
    }

    public void saveSignature() {
        sendFile();
    }

    public void uploadSignature(byte[] b) {
        ServiceCore serviceCore = new ServiceCore(getActivity());
        serviceCore.executeService(Definitions.PAYMENT_SIGNATURE, this, new Object[]{b}, null);
    }

    public byte[] compressSignature() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pad.getTransparentSignatureBitmap().compress(Bitmap.CompressFormat.PNG, 40, baos); //bm is the bitmap object
        Bitmap img = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
        if (img.getHeight() > 150) {
            baos = new ByteArrayOutputStream();
            if (img.getWidth() > 350) {
                img = getResizedBitmap(img, 350, 150);
            } else {
                img = getResizedBitmap(img, 350, img.getHeight());
            }
            img.compress(Bitmap.CompressFormat.PNG, 40, baos);

        } else {
            baos = new ByteArrayOutputStream();
            if (img.getWidth() > 350) {
                img = getResizedBitmap(img, 350, img.getHeight());
            } else {
                img = getResizedBitmap(img, img.getWidth(), img.getHeight());
            }
            img.compress(Bitmap.CompressFormat.PNG, 40, baos);
        }

        return baos.toByteArray();
    }

    private void sendFile() {
        saveFile1(context);
        saveFile2(context);
        if (!pad.isEmpty()) {
            saveFile1(context);
            saveFile2(context);
            if ((lengt0 + 3) > length1) {
                final AlertDialog aux;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(Definitions.SR_PAGO());
                builder.setMessage(Definitions.SIGN_PLEASE());
                builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                aux = builder.create();
                aux.show();
                aux.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#8ec553"));
                aux.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8ec553"));
            } else {
                lblAccept.setEnabled(false);
                progressDialog = ProgressDialog.show(context, Definitions.EMPTY(), Definitions.SIGN_UPLOADING(), true);

                byte[] b = compressSignature();
                String imgData;
                imgData = Base64.encodeToString(b, Base64.DEFAULT);

                saveSignature(imgData);
                uploadSignature(b);

//                WebServiceConnection webServiceConnection = new WebServiceConnection(context, Definitions.PAYMENT_SIGNATURE, PixzelleWebServiceConnection.POST);
//                webServiceConnection.setListFener(this);
//                webServiceConnection.execute(b);
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Definitions.SR_PAGO());
            builder.setMessage(Definitions.SIGN_PLEASE());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }


    public void onNoInternetConnection(int i) {
        if (Definitions.PAYMENT_SIGNATURE != i) {
            lblAccept.setEnabled(true);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Definitions.SR_PAGO());
            builder.setMessage(Definitions.SIGN_NO_INTERNET());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendFile();
                }
            }).setNegativeButton(getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else
            finishSignature();
    }

    public void onTimeoutConnection(int i) {
        if (i != Definitions.PAYMENT_SIGNATURE) {
            lblAccept.setEnabled(true);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Definitions.SR_PAGO());
            builder.setMessage(Definitions.SIGN_SERVER_OCCUPIED());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendFile();
                }
            }).setNegativeButton(getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else
            finishSignature();
    }

    public void onUnknownConnectionError(int i) {
        if (i != Definitions.PAYMENT_SIGNATURE) {
            lblAccept.setEnabled(true);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Definitions.SR_PAGO());
            builder.setMessage(Definitions.SIGN_UNKNOWN_ERROR());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendFile();
                }
            }).setNegativeButton(getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {
            finishSignature();
        }
    }

    @Override
    public void onConnectionErrorYesClick(int webService) {

    }

    @Override
    public void onConnectionErrorNoClick(int webService) {

    }

    @Override
    public void onResponseOk(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        if (!isFromOtherApp()) {
            Global.setBooleanPreference(this.getContext(), Definitions.IS_SIGNATURE_COMPLETED, true);
            Global.clearPreference(this.getContext(), Definitions.OPERATION_SIGNATURE);
        }
        Global.clearPreference(this.getContext(), Definitions.IF_FROM_OTHER_APP);
        lblAccept.setEnabled(true);
        if (webService == Definitions.PAYMENT_SIGNATURE) {

//            RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
//            Realm realm = Realm.getInstance(realmConfig);
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            RealmResults<Signature> result = realm.where(Signature.class)
                    .equalTo("guid", Global.getStringKey(context, Definitions.KEY_EXT_TRANSACTION))
                    .findAll();
            result.deleteAllFromRealm();
            realm.commitTransaction();
        }
        try {
            if (response.getStatus()) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (context.getIntent().getExtras() == null || context.getIntent().getExtras().getString(Global.LINK_PACKAGE) == null) {
                    Intent returnIntent = new Intent();
                    SrPagoTransaction srPagoTransaction = (SrPagoTransaction) getActivity().getIntent().getSerializableExtra("sale");

                    returnIntent.putExtra("sale", srPagoTransaction);
                    returnIntent.putExtra(Definitions.LINK_RETURN_CARD_HOLDER, srPagoTransaction.getCardHolderName());
                    returnIntent.putExtra(Definitions.LINK_RETURN_CARD_NUMBER, srPagoTransaction.getCardNumber());
                    returnIntent.putExtra(Definitions.LINK_RETURN_CARD_TYPE, srPagoTransaction.getCardType());
                    returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_AUTH_NO, srPagoTransaction.getAuthorizationCode());
                    returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_FOLIO, srPagoTransaction.getTransactionId());
                    if (("").equals(srPagoTransaction.getTip())) {
                        returnIntent.putExtra(Definitions.LINK_TIP, "");
                    } else {
                        returnIntent.putExtra(Definitions.LINK_TIP, String.valueOf(srPagoTransaction.getTip()));
                    }
                    if (("").equals(srPagoTransaction.getAmount())) {
                        returnIntent.putExtra(Definitions.LINK_AMOUNT, "");
                    } else {
                        returnIntent.putExtra(Definitions.LINK_AMOUNT, String.valueOf(srPagoTransaction.getAmount()));
                    }
                    returnIntent.putExtra(Definitions.KEY_RECEIPT(), context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT));
                    context.setResult(Activity.RESULT_OK, returnIntent);
                    context.finish();

                    //((OnSignatureCompleteListener)getActivity().getIntent().getSerializableExtra("listener")).onSignatureComplete(returnIntent);
                    ServiceCoreTransactionV2.onSignatureCompleteListener.onSignatureComplete(returnIntent);
                } else {
                    toOtherApp(TicketsResponse.parseTransaction(context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT)));
                }
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            Intent returnIntent = new Intent();
            context.setResult(10001, returnIntent);
            context.finish();
        }
    }

    private void toOtherApp(SrPagoTransaction srPagoTransaction) {
        Intent returnIntent = new Intent();
        returnIntent.setAction(Intent.ACTION_SEND);
        returnIntent.putExtra(Definitions.KEY_RECEIPT(), context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT));
        returnIntent.putExtra(Definitions.LINK_RETURN_CARD_HOLDER, srPagoTransaction.getCardHolderName());
        returnIntent.putExtra(Definitions.LINK_RETURN_CARD_NUMBER, srPagoTransaction.getCardNumber());
        returnIntent.putExtra(Definitions.LINK_RETURN_CARD_TYPE, srPagoTransaction.getCardType());
        returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_AUTH_NO, srPagoTransaction.getAuthorizationCode());
        returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_FOLIO, srPagoTransaction.getTransactionId());
        if (("").equals(srPagoTransaction.getTip())) {
            returnIntent.putExtra(Definitions.LINK_TIP, "");
        } else {
            returnIntent.putExtra(Definitions.LINK_TIP, String.valueOf(srPagoTransaction.getTip()));
        }
        if (("").equals(srPagoTransaction.getAmount())) {
            returnIntent.putExtra(Definitions.LINK_AMOUNT, "");
        } else {
            returnIntent.putExtra(Definitions.LINK_AMOUNT, String.valueOf(srPagoTransaction.getAmount()));
        }
        if (context.getParent() == null) {
            context.setResult(Activity.RESULT_OK, returnIntent);
        } else {
            context.getParent().setResult(Activity.RESULT_OK, returnIntent);
        }
        context.finish();
        ServiceCoreTransactionV2.onSignatureCompleteListener.onSignatureComplete(returnIntent);
        //((OnSignatureCompleteListener)getActivity().getIntent().getSerializableExtra("listener")).onSignatureComplete(returnIntent);
    }

    @Override
    public void onResponseError(@Pixzelle.SERVER_CODES int code, SPResponse response, int webService) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (webService != Definitions.PAYMENT_SIGNATURE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Definitions.SR_PAGO());
            builder.setMessage(Definitions.SIGN_UNKNOWN_ERROR());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendFile();
                }
            }).setNegativeButton(getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#8ec553"));
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8ec553"));
        } else {

            try {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (context.getIntent().getExtras() == null || context.getIntent().getExtras().getString(Global.LINK_PACKAGE) == null) {
                    Intent returnIntent = new Intent();
                    SrPagoTransaction srPagoTransaction = (SrPagoTransaction) getActivity().getIntent().getSerializableExtra("sale");

                    returnIntent.putExtra("sale", srPagoTransaction);
                    returnIntent.putExtra(Definitions.LINK_RETURN_CARD_HOLDER, srPagoTransaction.getCardHolderName());
                    returnIntent.putExtra(Definitions.LINK_RETURN_CARD_NUMBER, srPagoTransaction.getCardNumber());
                    returnIntent.putExtra(Definitions.LINK_RETURN_CARD_TYPE, srPagoTransaction.getCardType());
                    returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_AUTH_NO, srPagoTransaction.getAuthorizationCode());
                    returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_FOLIO, srPagoTransaction.getTransactionId());
                    if (("").equals(srPagoTransaction.getTip())) {
                        returnIntent.putExtra(Definitions.LINK_TIP, "");
                    } else {
                        returnIntent.putExtra(Definitions.LINK_TIP, String.valueOf(srPagoTransaction.getTip()));
                    }
                    if (("").equals(srPagoTransaction.getAmount())) {
                        returnIntent.putExtra(Definitions.LINK_AMOUNT, "");
                    } else {
                        returnIntent.putExtra(Definitions.LINK_AMOUNT, String.valueOf(srPagoTransaction.getAmount()));
                    }
                    returnIntent.putExtra(Definitions.KEY_RECEIPT(), context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT));
                    context.setResult(Activity.RESULT_OK, returnIntent);
                    context.finish();

                    //((OnSignatureCompleteListener)getActivity().getIntent().getSerializableExtra("listener")).onSignatureComplete(returnIntent);
                    ServiceCoreTransactionV2.onSignatureCompleteListener.onSignatureComplete(returnIntent);
                } else {
                    toOtherApp(TicketsResponse.parseTransaction(context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT)));
                }
            } catch (Exception ex) {
                Logger.logError(ex);
                Intent returnIntent = new Intent();
                context.setResult(10001, returnIntent);
                context.finish();
            }

//            finishSignature();
/*
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Definitions.SR_PAGO());
            builder.setMessage(Definitions.SIGN_NO_INTERNET());
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.sr_pago_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendFile();
                }
            }).setNegativeButton(getResources().getString(R.string.sr_pago_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lblAccept.setEnabled(true);
                    dialog.dismiss();
                }
            });
            builder.create().show();
            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#8ec553"));
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8ec553"));*/
        }
    }


    public void saveFile1(Context context) {
        File file = new File(context.getFilesDir(), Definitions.SIGNATURE_JPG1());
        try {
            Bitmap canvasBitmap = pad.getSignatureBitmap();
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            canvasBitmap = Bitmap.createBitmap(canvasBitmap, 0, 0, canvasBitmap.getWidth(), canvasBitmap.getHeight(),
                    matrix, true);
            canvasBitmap.compress(Bitmap.CompressFormat.JPEG, 40, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();
            length1 = (int) (file.length() / 1024);
        } catch (Exception ex) {
            Logger.logError(ex);
        }
    }

    public void saveFile2(Context context) {
        File file = new File(context.getFilesDir(), Definitions.SIGNATURE_JPG2());
        try {

            Bitmap canvasBitmap = pad.getTransparentSignatureBitmap();

            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            canvasBitmap = Bitmap.createBitmap(canvasBitmap, 0, 0, canvasBitmap.getWidth(), canvasBitmap.getHeight(),
                    matrix, true);
            canvasBitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            lengt0 = (int) (file.length() / 1024);
        } catch (Exception ex) {
            Logger.logError(ex);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public void saveSignature(String img) {

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Signature signature = realm.createObject(Signature.class);

        signature.setUser(Global.getStringKey(context, Definitions.KEY_USER));
        signature.setData(img);

        String tempToken = Global.getStringKey(context, Definitions.KEY_TRANSACTION_TOKEN);
        String tempGui = Global.getStringKey(context, Definitions.KEY_EXT_TRANSACTION);

        if (tempToken != null && !tempToken.equals("pxlNull") && !tempToken.equals("")) {
            signature.setToken(tempToken);
        } else {
            Global.setStringKey(context, Definitions.KEY_TRANSACTION_TOKEN, token);
//            signature.setToken(Global.getStringKey(context, Definitions.KEY_TRANSACTION_TOKEN));
            signature.setToken(token);
        }

        if (tempGui != null && !tempGui.equals("pxlNull") && !tempGui.equals("")) {
            signature.setGuid(tempGui);
        } else {
            Global.setStringKey(context, Definitions.KEY_EXT_TRANSACTION, numTransaction);
//            signature.setGuid(Global.getStringKey(context, Definitions.KEY_EXT_TRANSACTION));
            signature.setGuid(numTransaction);
        }

        realm.commitTransaction();
    }

    public void finishSignature() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (context.getIntent().getExtras() == null || context.getIntent().getExtras().getString(Global.LINK_PACKAGE) == null) {
                Intent returnIntent = new Intent();
                SrPagoTransaction srPagoTransaction = (SrPagoTransaction) getActivity().getIntent().getSerializableExtra("sale");//TicketsResponse.parseTransaction(context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT));

                returnIntent.putExtra(Definitions.LINK_RETURN_CARD_HOLDER, srPagoTransaction.getCardHolderName());
                returnIntent.putExtra(Definitions.LINK_RETURN_CARD_NUMBER, srPagoTransaction.getCardNumber());
                returnIntent.putExtra(Definitions.LINK_RETURN_CARD_TYPE, srPagoTransaction.getCardType());
                returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_AUTH_NO, srPagoTransaction.getAuthorizationCode());
                returnIntent.putExtra(Definitions.LINK_RETURN_OPERATION_FOLIO, srPagoTransaction.getTransactionId());
                if (("").equals(srPagoTransaction.getTip())) {
                    returnIntent.putExtra(Definitions.LINK_TIP, "");
                } else {
                    returnIntent.putExtra(Definitions.LINK_TIP, String.valueOf(srPagoTransaction.getTip()));
                }
                if (("").equals(srPagoTransaction.getAmount())) {
                    returnIntent.putExtra(Definitions.LINK_AMOUNT, "");
                } else {
                    returnIntent.putExtra(Definitions.LINK_AMOUNT, String.valueOf(srPagoTransaction.getAmount()));
                }
                returnIntent.putExtra(Definitions.KEY_RECEIPT(), context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT));
                context.setResult(Activity.RESULT_OK, returnIntent);
                context.finish();
                ServiceCoreTransactionV2.onSignatureCompleteListener.onSignatureComplete(returnIntent);
                //((OnSignatureCompleteListener)getActivity().getIntent().getSerializableExtra("listener")).onSignatureComplete(returnIntent);
            } else {
                toOtherApp((SrPagoTransaction) getActivity().getIntent().getSerializableExtra("sale"));//TicketsResponse.parseTransaction(context.getIntent().getStringExtra(Definitions.KEY_DATA_RECEIPT)));
            }
        } catch (Exception ex) {
            Logger.logError(ex);
            Intent returnIntent = new Intent();
            context.setResult(10001, returnIntent);
            context.finish();
            //((OnSignatureCompleteListener)getActivity().getIntent().getSerializableExtra("listener")).onSignatureComplete(null);
            ServiceCoreTransactionV2.onSignatureCompleteListener.onSignatureComplete(null);
        }
    }

    private boolean isFromOtherApp() {
        return Global.getBooleanPreference(this.getContext(), Definitions.IF_FROM_OTHER_APP);
    }
}
