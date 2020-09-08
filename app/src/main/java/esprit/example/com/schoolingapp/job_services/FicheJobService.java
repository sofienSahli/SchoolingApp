package esprit.example.com.schoolingapp.job_services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import esprit.example.com.schoolingapp.R;
import esprit.example.com.schoolingapp.activities.EspaceAgentActivity;
import esprit.example.com.schoolingapp.entities.FichePFE;
import esprit.example.com.schoolingapp.entities.UnseenFiches;
import esprit.example.com.schoolingapp.local_storage.AppDatabase;
import esprit.example.com.schoolingapp.services.implementations.FicheServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//TODO make it undying service
public class FicheJobService extends JobService implements Callback<List<FichePFE>> {
    private final String CHANNEL_ID = "101";
    private final int notificationId = 120;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("Fiches", "FicheJobService has started");
        FicheServices ficheServices = new FicheServices();
        ficheServices.get_unseen_fiches(this);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("Stop job", "Job stop ");

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName name = new ComponentName(this, FicheJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(101, name).
                setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setMinimumLatency(10000)
                .setPersisted(true)

                .build();
        jobScheduler.schedule(jobInfo);
        return true;
    }

    @Override
    public void onResponse(Call<List<FichePFE>> call, Response<List<FichePFE>> response) {
        if (response.code() == 200) {
            if (response.body() != null && !response.body().isEmpty()) {
                for (FichePFE f : response.body()) {
                    UnseenFiches unseenFiches = AppDatabase.getAppDatabase(getApplicationContext()).unseenFichesDAO().find(f.getId());
                    if (unseenFiches == null) {
                        unseenFiches = new UnseenFiches();
                        unseenFiches.setFiche_id(f.getId());
                        unseenFiches.setSeen(false);
                        AppDatabase.getAppDatabase(getApplicationContext()).unseenFichesDAO().insertAll(unseenFiches);
                        notify_user(f);
                    } else if (!unseenFiches.isSeen()) {
                        notify_user(f);

                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call<List<FichePFE>> call, Throwable t) {
        Log.e("Fiches services", t.getMessage());
    }

    private void notify_user(FichePFE fichePFE) {
        Log.e("Notify", "Notification ");
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Gestionaire de fiche FPE ";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        Intent intent = new Intent(this, EspaceAgentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Nouvel fiche")
                .setContentText("Une nouvelle fiche est disponible pour le traitement")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(fichePFE.getStudent().getName() + " " + fichePFE.getStudent().getLast_name() + " a upload une sa fiche PFE  "))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.createNotificationChannel(mChannel);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

        onStopJob(null);
    }

}
