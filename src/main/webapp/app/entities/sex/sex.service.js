(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('Sex', Sex);

    Sex.$inject = ['$resource'];

    function Sex ($resource) {
        var resourceUrl =  'api/sexes/:id';
        var findByPersonUrl =  'api/sexes/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
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
