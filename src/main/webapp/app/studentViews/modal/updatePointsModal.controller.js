angular.module('hopeRanchLearningAcademyApp').controller('ModalInstanceCtrl', function ($scope, $timeout, $uibModalInstance, $stateParams, modalData, entity, Student, Teacher, Principal, Point_entry, Social_skill, Student_social_skill, Enrolled_SSS) {

    $scope.pointVal;

    Principal.identity().then(function(account) {
        $scope.account = account;
          console.log(account);
//            console.log(account.authorities[0]);
        $scope.isAuthenticated = Principal.isAuthenticated;
    });

    $scope.modalData = modalData;
    console.log("modalData = ");
    console.log($scope.modalData);

    $scope.point_Entry = entity;
    console.log("point_entry model = ");
    console.log($scope.point_Entry);



    $scope.currentClassroom = function(teachers) {
        for (var i=0;i<teachers.length;i++){
            if (teachers[i].email.toLowerCase() == $scope.account.email.toLowerCase()){
                $scope.currentClassID =  teachers[i].classroom.id;
                $scope.currentClassName = teachers[i].classroom.name;
                $scope.teacherName = teachers[i].first_name + " " + teachers[i].last_name;
                $scope.teacherId = teachers[i].id;
                $scope.teacherObj = teachers[i];
                console.log("The current classroom ID is: " + $scope.currentClassID);
                console.log(teachers[i]);
            }
        }
    }
    Teacher.query(function(result) {
       $scope.teachers = result;
       console.log($scope.teachers[0]);
       $scope.currentClassroom(result);
    });

    $scope.getFullSssObj = function() {
        Enrolled_SSS.get({stuId: modalData.student.id, skillId: modalData.skillId}, function(result){
            console.log(result);
            for (var i=0; i<result.sssRecord.length; i++) {
                if (result.sssRecord[i].social_skill.id == modalData.skillId){
                    console.log("This is the full Skill Record to Update");
                    console.log(result.sssRecord[i]);
                }
            }
        });
    }

     $scope.images = [
     {'imageUrl': 'content/images/StuModalPhotoArray/good-job-dog.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/good-job-ribbon.png'},
     {'imageUrl': 'content/images/StuModalPhotoArray/good-job-smile.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/great-job-rainbow.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/great-job-smile.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/seal-of-approval.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/seal-of-approval2.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/well-done.jpg'},
     {'imageUrl': 'content/images/StuModalPhotoArray/great-job-tigger.jpg'},
     ];

     $scope.getRandomImg = function(){
         var randomNum = Math.floor(Math.random() * $scope.images.length);
         $scope.happyImgUrl = $scope.images[randomNum].imageUrl;
     }

    $scope.saveEntry = function(){
        if ($scope.modalData.action == 'add'){
            $scope.getRandomImg();
            $scope.showImg = true;
            $timeout(function(){
                $scope.buildFullEntryObject();
            }, 2000);
        } else {
            $scope.showImg = false;
            $scope.buildFullEntryObject();
        }
    };


    $scope.buildFullEntryObject = function() {
        $scope.point_Entry.id = null;
        $scope.point_Entry.ent_value = $scope.point_Entry.ent_value;
        $scope.point_Entry.ent_submission_time = new Date();
        $scope.point_Entry.ent_action_time = null;
        $scope.point_Entry.teacher = $scope.teacherObj;
        $scope.point_Entry.student = $scope.modalData.student;

        if ($scope.modalData.action == "add") {
            if ($scope.point_Entry.ent_value > 0)
                $scope.point_Entry.ent_value = $scope.point_Entry.ent_value;
            else
                $scope.point_Entry.ent_value = $scope.point_Entry.ent_value;
                //remove line above, reject entry and show an error
        }
        else {
            if ($scope.point_Entry.ent_value <= 0)
                $scope.point_Entry.ent_value = $scope.point_Entry.ent_value;
            else
                $scope.point_Entry.ent_value = -$scope.point_Entry.ent_value;
        }

        console.log($scope.point_Entry.ent_value);
        console.log($scope.modalData.action);

        Social_skill.get({id: $scope.modalData.skillId}, function(result) {
            console.log(result);
            $scope.social_skill_obj = result;
            $scope.point_Entry.social_skill = $scope.social_skill_obj;
            $scope.save();
        });

        //console.log($scope.social_skill_obj);
        //$scope.point_Entry.social_skill = $scope.social_skill_obj;
        $scope.getFullSssObj();
    };

    var onSaveSuccess = function (result) {
        $scope.$emit('hopeYouthRanchApp:point_EntryUpdate', result);
        var newSkillPtsAndId = {}
        newSkillPtsAndId.points = $scope.point_Entry.ent_value;
        newSkillPtsAndId.id = $scope.modalData.skillId;
        $uibModalInstance.close(newSkillPtsAndId);
        $scope.isSaving = false;
    };

    var onSaveError = function (result) {
        $scope.isSaving = false;
        $uibModalInstance.close("noSubmission");
    };

    $scope.save = function () {
        $scope.isSaving = true;
//        console.log(pointVal);

        console.log($scope.point_Entry);

        if ($scope.point_Entry.id != null) {
            Point_entry.update($scope.point_Entry, onSaveSuccess, onSaveError);
        } else {
            Point_entry.save($scope.point_Entry, onSaveSuccess, onSaveError);
        }
    };



    $scope.ok = function () {
        /*$uibModalInstance.close($scope.selected.item);*/
        $uibModalInstance.close('message passed back');
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };



    });
