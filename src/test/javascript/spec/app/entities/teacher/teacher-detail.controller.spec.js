'use strict';

describe('Controller Tests', function() {

    describe('Teacher Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTeacher, MockClassroom, MockPoint_entry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockClassroom = jasmine.createSpy('MockClassroom');
            MockPoint_entry = jasmine.createSpy('MockPoint_entry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Teacher': MockTeacher,
                'Classroom': MockClassroom,
                'Point_entry': MockPoint_entry
            };
            createController = function() {
                $injector.get('$controller')("TeacherDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hopeRanchLearningAcademyApp:teacherUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
