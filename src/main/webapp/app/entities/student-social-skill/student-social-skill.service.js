(function() {
    'use strict';
    angular
        .module('hopeRanchLearningAcademyApp')
        .factory('Student_social_skill', Student_social_skill)
        .factory('Student_Skills', Student_Skills)
        .factory('Enrolled_SSS', Enrolled_SSS);

    Student_social_skill.$inject = ['$resource', 'DateUtils'];
    Student_Skills.$inject = ['$resource', 'DateUtils'];
    Enrolled_SSS.$inject = ['$resource', 'DateUtils'];

    function Student_social_skill ($resource, DateUtils) {
        var resourceUrl =  'api/student-social-skills/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.start_date = DateUtils.convertLocalDateFromServer(data.start_date);
                    data.end_date = DateUtils.convertLocalDateFromServer(data.end_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.start_date = DateUtils.convertLocalDateToServer(data.start_date);
                    data.end_date = DateUtils.convertLocalDateToServer(data.end_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.start_date = DateUtils.convertLocalDateToServer(data.start_date);
                    data.end_date = DateUtils.convertLocalDateToServer(data.end_date);
                    return angular.toJson(data);
                }
            }
        })
    }
    function Student_Skills ($resource, DateUtils) {
        return $resource('api/studentSkills/:stuId', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    console.log(data);

//                        if (data = []){
//                            console.log("null data");
//                            var skills = {};
//                            skills.skill = [];
//                            return skills;
//                        } else {
                        console.log("data present");

                        var skills = data;
                        //create empty skills object and place array of skills inside skill attribute
                        var data = {};
                        data.skills = skills;
                        console.log(data);


    //                    data.start_date = DateUtils.convertLocaleDateFromServer(data.start_date);
    //                    data.end_date = DateUtils.convertLocaleDateFromServer(data.end_date);
                        return data;
//                        }
                }
            }
        });
    }
    function Enrolled_SSS ($resource, DateUtils) {
        return $resource('api/sss/:stuId/:skillId', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    //console.log(data);

                        var sssRecord = data;
                        //create empty skills object and place array of skills inside skill attribute
                        var data = {};
                        data.sssRecord = sssRecord;
                        //console.log(data);

    //                    data.start_date = DateUtils.convertLocaleDateFromServer(data.start_date);
    //                    data.end_date = DateUtils.convertLocaleDateFromServer(data.end_date);
                        return data;
//                        }
                }
            }
        });
    }
})();
