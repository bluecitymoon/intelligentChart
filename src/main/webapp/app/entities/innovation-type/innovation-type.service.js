(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('InnovationType', InnovationType);

    InnovationType.$inject = ['$resource'];

    function InnovationType ($resource) {
        var resourceUrl =  'api/innovation-types/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
