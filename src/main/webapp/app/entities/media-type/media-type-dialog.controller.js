(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('MediaTypeDialogController', MediaTypeDialogController);

    MediaTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MediaType'];

    function MediaTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MediaType) {
        var vm = this;

        vm.mediaType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mediaType.id !== null) {
                MediaType.update(vm.mediaType, onSaveSuccess, onSaveError);
            } else {
                MediaType.save(vm.mediaType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:mediaTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
