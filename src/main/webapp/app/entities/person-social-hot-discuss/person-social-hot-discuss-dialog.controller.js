(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSocialHotDiscussDialogController', PersonSocialHotDiscussDialogController);

    PersonSocialHotDiscussDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonSocialHotDiscuss', 'Person'];

    function PersonSocialHotDiscussDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonSocialHotDiscuss, Person) {
        var vm = this;

        vm.personSocialHotDiscuss = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personSocialHotDiscuss.id !== null) {
                PersonSocialHotDiscuss.update(vm.personSocialHotDiscuss, onSaveSuccess, onSaveError);
            } else {
                PersonSocialHotDiscuss.save(vm.personSocialHotDiscuss, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personSocialHotDiscussUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
