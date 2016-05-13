'use strict';

describe('Controller Tests', function() {

    describe('Student Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudent, MockClassroom, MockStudent_social_skill, MockPoint_entry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudent = jasmine.createSpy('MockStudent');
            MockClassroom = jasmine.createSpy('MockClassroom');
            MockStudent_social_skill = jasmine.createSpy('MockStudent_social_skill');
            MockPoint_entry = jasmine.createSpy('MockPoint_entry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Student': MockStudent,
                'Classroom': MockClassroom,
                'Student_social_skill': MockStudent_social_skill,
                'Point_entry': MockPoint_entry
            };
            createController = function() {
                $injector.get('$controller')("StudentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hopeRanchLearningAcademyApp:studentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
